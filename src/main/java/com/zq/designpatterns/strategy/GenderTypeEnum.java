package com.zq.designpatterns.strategy;

/**
 * @author zhangqing
 * @Package com.zq.designpatterns.strategy
 * @date 2020/7/25 9:52
 */
public enum GenderTypeEnum implements BaseGender{

    /**
     * 男
     */
    MAN(1, "man", "男"),
    /**
     * 女
     */
    WOMAN(2, "woman", "女"),
    /**
     * 不确定
     */
    OTHER(3, "other", "不确定");

    private Integer code;
    private String codeEn;
    private String codeCn;

    GenderTypeEnum(Integer code, String codeEn, String codeCn) {
        this.code = code;
        this.codeEn = codeEn;
        this.codeCn = codeCn;
    }

    /**
     * 根据type解析
     *
     * @param type
     * @return
     */
    public static GenderTypeEnum parse(int type) {
        GenderTypeEnum[] types = GenderTypeEnum.values();
        for (GenderTypeEnum cm : types) {
            if (cm.code.equals(type)) {
                return cm;
            }
        }
        return null;
    }


    public static GenderTypeEnum parseByCodeEn(String codeEn) {
        GenderTypeEnum[] types = GenderTypeEnum.values();
        for (GenderTypeEnum cm : types) {
            if (cm.codeEn.equals(codeEn)) {
                return cm;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getCodeEn() {
        return codeEn;
    }

    public String getCodeCn() {
        return codeCn;
    }

    public GenderTypeEnum setCode(Integer code) {
        this.code = code;
        return this;
    }

    public GenderTypeEnum setCodeEn(String codeEn) {
        this.codeEn = codeEn;
        return this;
    }

    public GenderTypeEnum setCodeCn(String codeCn) {
        this.codeCn = codeCn;
        return this;
    }
}
