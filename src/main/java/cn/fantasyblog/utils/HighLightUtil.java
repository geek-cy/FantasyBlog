package cn.myBlog.utils;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.Map;

/**
 * @Description
 * @Author Cy
 * @Date 2021-05-09 22:33
 */
public class HighLightUtil {
    public static void parseField(SearchHit hit,String field,Map<String, Object> map){
        // 高亮结果
        Map<String, HighlightField> highlightMap = hit.getHighlightFields();
        HighlightField title = highlightMap.get(field);
        // 解析高亮字段,此处若不判断会报空指针
        if(title != null){
            // 高亮片段
            Text[] fragments = title.fragments();
            StringBuilder newTitle = new StringBuilder();
            for(Text fragment : fragments){
                newTitle.append(fragment);
            }
            // 高亮文本替换原来内容
            map.put(field,newTitle.toString());
        }
    }
}
