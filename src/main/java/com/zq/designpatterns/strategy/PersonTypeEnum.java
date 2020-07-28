package com.zq.designpatterns.strategy;

/**
 * @author zhangqing
 * @Package com.zq.designpatterns.strategy
 * @date 2020/7/25 9:55
 */
public enum PersonTypeEnum implements BaseGender {
    /**
     * 男
     */
    WOMAN(1, "man", "女"),
    /**
     * 女
     */
    MAN(2, "woman", "男"),
    /**
     * 不确定
     */
    OTHER(0, "other", "不确定");

    private Integer code;
    private String codeEn;
    private String codeCn;

    PersonTypeEnum(Integer code, String codeEn, String codeCn) {
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
    public static PersonTypeEnum parse(int type) {
        PersonTypeEnum[] types = PersonTypeEnum.values();
        for (PersonTypeEnum cm : types) {
            if (cm.code.equals(type)) {
                return cm;
            }
        }
        return null;
    }


    public static PersonTypeEnum parseByCodeEn(String codeEn) {
        PersonTypeEnum[] types = PersonTypeEnum.values();
        for (PersonTypeEnum cm : types) {
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

    public PersonTypeEnum setCode(Integer code) {
        this.code = code;
        return this;
    }

    public PersonTypeEnum setCodeEn(String codeEn) {
        this.codeEn = codeEn;
        return this;
    }

    public PersonTypeEnum setCodeCn(String codeCn) {
        this.codeCn = codeCn;
        return this;
    }
}
