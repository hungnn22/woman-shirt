package com.ws.masterserver.utils.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hungnn22
 */
public class HtmlUtils {

    public static List<String> convert4Menu(String raw) {
        try {
            return Arrays.stream(raw.split("\n")).map(String::new).map(String::trim).collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
