package com.jeebud.common.util;

import lombok.Data;

import java.io.InputStream;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class ZipFile {
    private InputStream inputStream;
    private String name;
}
