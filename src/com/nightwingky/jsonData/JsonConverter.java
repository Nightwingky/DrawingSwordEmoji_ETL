package com.nightwingky.jsonData;

import com.google.gson.Gson;
import com.nightwingky.bean.DialogueBean;
import com.nightwingky.bean.StandardDialogueBean;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    public static List<StandardDialogueBean> getStandardDialogue(String jsonLine) {

        List<StandardDialogueBean> standardDialogueBeanList = new ArrayList<>();

        Gson gson = new Gson();

        DialogueBean dialogueBean = gson.fromJson(jsonLine, DialogueBean.class);

        /**
         思路：
         1. 本行json共有几条台词？
         2. foreach循环words_result
         3. 每循环到一项创建一个StandardDialogueVO对象
         4. 将信息录入后插入到standardDialogueVOList
         5. 返回将信息录入后插入到standardDialogueVOList
         */
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

        return standardDialogueBeanList;
    }
}
