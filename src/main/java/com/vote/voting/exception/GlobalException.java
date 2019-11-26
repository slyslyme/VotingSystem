package com.vote.voting.exception;

import com.vote.voting.result.CodeMsg;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 15:02
 * @package com.vote.voting.exception
 **/
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private CodeMsg cm;
    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

    public void setCm(CodeMsg cm) {
        this.cm = cm;
    }
}
