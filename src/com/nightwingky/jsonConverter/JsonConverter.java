package com.nightwingky.jsonConverter;

import com.google.gson.Gson;
import com.nightwingky.bean.DialogueVO;
import com.nightwingky.bean.StandardDialogueVO;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    public static List<StandardDialogueVO> getStandardDialogue(String jsonLine) {

        List<StandardDialogueVO> standardDialogueVOList = new ArrayList<>();

        Gson gson = new Gson();

        DialogueVO dialogueVO = gson.fromJson(jsonLine, DialogueVO.class);

        /**
         思路：
         1. 本行json共有几条台词？
         2. foreach循环words_result
         3. 每循环到一项创建一个StandardDialogueVO对象
         4. 将信息录入后插入到standardDialogueVOList
         5. 返回将信息录入后插入到standardDialogueVOList
         */
        List<DialogueVO.WordsBean> words_result = dialogueVO.getWords_result();
        for (DialogueVO.WordsBean w : words_result) {
            StandardDialogueVO s = new StandardDialogueVO();
            //设置log_id，判断是否有多个log_id，使用不同方法处理
            String ss = dialogueVO.getLog_id().toString();
            if (ss.substring(0, 1).equals("[")) {
                List<String> log_id = (List<String>) dialogueVO.getLog_id();
                s.setLog_id(log_id.get(0));
            } else {
                s.setLog_id(ss);
            }
            //设置probability和word
            s.setVariance(Double.parseDouble(w.getProbability().getVariance()));
            s.setAverage(Double.parseDouble(w.getProbability().getAverage()));
            s.setMin(Double.parseDouble(w.getProbability().getMin()));
            s.setWords(w.getWords());

            standardDialogueVOList.add(s);
        }

        return standardDialogueVOList;
    }
}
