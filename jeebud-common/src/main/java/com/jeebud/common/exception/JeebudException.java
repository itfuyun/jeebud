package com.jeebud.common.exception;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class JeebudException extends RuntimeException {
    public JeebudException(){
        super();
    }

    public JeebudException(String message) {
        super(message);
    }
}
