package org.lf.community.cache;

import org.apache.commons.lang3.StringUtils;
import org.lf.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> getTag() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO tagDTO = new TagDTO();
        tagDTO.setCategoryName("开发语言");
        tagDTO.setTags(Arrays.asList("java", "c语言", "php", "html"));
        tagDTOS.add(tagDTO);

        TagDTO tagDTO2 = new TagDTO();
        tagDTO2.setCategoryName("平台框架");
        tagDTO2.setTags(Arrays.asList("spring", "flask", "struts"));
        tagDTOS.add(tagDTO2);

        TagDTO tagDTO3 = new TagDTO();
        tagDTO3.setCategoryName("服务器");
        tagDTO3.setTags(Arrays.asList("tomcat", "nginx", "docker", "apache"));
        tagDTOS.add(tagDTO3);

        return tagDTOS;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO> tagDTOS = getTag();
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
