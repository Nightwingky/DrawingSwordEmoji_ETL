# 《亮剑》表情包数据处理

* 该部分的所有数据基于[《亮剑》表情包索引工具](https://github.com/nevertiree/DrawingSwordEmoji)

## 数据格式

```json
{
	"log_id": "01_11525",
	"words_result_num": 1,
	"words_result": [{
		"words": "全休上刺刀准备进攻",
		"probability": {
			"variance": 0.019966,
			"average": 0.901787,
			"min": 0.592296
		}
	}]
}
```
```json
{
	"log_id": ["01_15550", "01_15575"],
	"words_result_num": 3,
	"words_result": [{
		"words": "楚云飞上校",
		"probability": {
			"variance": 8.7e-05,
			"average": 0.994517,
			"min": 0.976029
		}
	}, {
		"words": "国民党晋绥军358团团长",
		"probability": {
			"variance": 0.026446,
			"average": 0.876162,
			"min": 0.466501
		}
	}, {
		"words": "黄埔军校五期毕业",
		"probability": {
			"variance": 0.024579,
			"average": 0.896254,
			"min": 0.527949
		}
	}]
}
```

通过观察我们发现，由于百度OCR的问题，提取出来的json数据格式并不统一，存在大量多层嵌套的json数据。但是，通过进一步观察，我们发现，存在多层嵌套的只有"log_id"和"words_result"字段，并且这两个字段的嵌套存在一定的规律。因此，我们决定不使用传统ETL工具来处理这些数据，而是使用Google的[gson](https://github.com/google/gson) library来处理。

## 处理目标

将上面多层嵌套的json数据解开，处理成能够放进关系型数据库的数据，如下表。

|id|log_id|variance|average|min|words|
|-|-|-|-|-|-|
|56|01_09650|0.000219|0.988974|0.968062|李云龙|
|242|01_23175|0.000089|0.994187|0.964197|新一团团长李云龙决心率全团|
|251|01_23825|0.001345|0.97433|0.886485|新一团团长是李云龙|
|336|01_33950|0.003657|0.967911|0.812379|这个李云龙是从哪儿蹦出来的|
|363|01_36800|0.000017|0.997365|0.990049|只要我李云龙在|

### 处理思路

1. 如果一条json数据包含多个"log_id"，意味着有多张图对应一句台词。我们将台词作为关键字进行搜索，搜到哪一张图都是OK的。因此，我们只需要保留一个log_id即可。

![01_10125](img/10125.jpg)
![01_10150](img/10150.jpg)

1. 如果一条json数据包含多个"words_result"，意味着截取下来的这一帧里面有多条文字，或者一句台词被截断了。我们无论搜索哪一句台词，结果都应该是这张图。因此，我们将这条json数据里的每一个words提取出来，分别创建一条新的记录，使用统一的log_id即可。

![36_32875](img/32875.jpg)

## 处理办法

### 1. 创建对应json数据格式的JavaBean

* 注意事项
    * 内部嵌套的类必须是static的，否则解析会出错；
    * 类里面的属性名必须跟Json字段的Key一样；
    * 内部嵌套的用[]括起来的部分是一个List；

* 说明
    * 为使文档简洁这里省去了所有Getter Setter方法
    * 关于log_id字段类型：观察数据发现，log_id字段只有两种形式，分别是单一数据，如"01_11125"，或者是一个List如"["01_15550", "01_15575"]"。如果使用List<String>定义这个字段类型，gson库只能识别List形式的数据，而不能识别单一数据。因此，我们只能先将定义为Object类型，在后面的步骤使用Java处理。这里暂时没有找到更好的处理办法。
```java
package com.nightwingky.bean;

import java.util.List;

public class DialogueBean {

    private Object log_id;
    private String words_result_num;
    private List<WordsBean> words_result;

    public static class WordsBean {
        private ProbabilityBean probability;
        private String words;
    }

    public static class ProbabilityBean {
        private String variance;
        private String average;
        private String min;
    }
}
```

### 2. 把所有的json数据提取到一个List中

```java
Gson gson = new Gson();

DialogueBean dialogueBean = gson.fromJson(jsonLine, DialogueBean.class);
```

这里使用Gson.fromJson(String json, Class<T> classOfT)方法，该方法可将json数据转换成符合该json格式的JavaBean的对象，转换的原理和详细步骤我们暂时还没有理解，需要继续学习。
```java
public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
	Object object = this.fromJson((String)json, (Type)classOfT);
	return Primitives.wrap(classOfT).cast(object);
}
```

### 3. 创建对应关系型数据库表结构的JavaBean，将提取出来的数据标准化，放入第二个List

* 说明
	* 为使文档简洁，这里依然省略Getter Setter方法
	* 手动处理"log_id"字段的方法个人认为不够完美，正在寻找优化办法

```java
package com.nightwingky.bean;

public class StandardDialogueBean {
    private String log_id;
    private double variance;
    private double average;
    private double min;
    private String words;
}
```
```java

List<StandardDialogueBean> standardDialogueBeanList = new ArrayList<>();
List<DialogueBean.WordsBean> words_result = dialogueBean.getWords_result();
for (DialogueBean.WordsBean w : words_result) {
    StandardDialogueBean s = new StandardDialogueBean();
    //设置log_id，判断是否有多个log_id，使用不同方法处理
    String ss = dialogueBean.getLog_id().toString();
    if (ss.substring(0, 1).equals("[")) {
        List<String> log_id = (List<String>) dialogueBean.getLog_id();
        s.setLog_id(log_id.get(0));
    } else {
        s.setLog_id(ss);
    }
    //设置probability和word
    s.setVariance(Double.parseDouble(w.getProbability().getVariance()));
    s.setAverage(Double.parseDouble(w.getProbability().getAverage()));
    s.setMin(Double.parseDouble(w.getProbability().getMin()));
    s.setWords(w.getWords());

    standardDialogueBeanList.add(s);
}
```

### 4. 导入数据库或输出到文件
