package com.zq.designpatterns.strategy;

/**
 * @author zhangqing
 * @Package com.zq.designpatterns.strategy
 * @date 2020/7/25 9:58
 */
public enum AnimalTypeEnum implements BaseGender{

    /**
     * 男
     */
    WOMAN(1, "man", "雌性"),
    /**
     * 女
     */
    MAN(2, "woman", "雄性"),
    /**
     * 不确定
     */
    OTHER(0, "other", "不确定");

    private Integer code;
    private String codeEn;
    private String codeCn;

    AnimalTypeEnum(Integer code, String codeEn, String codeCn) {
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
    public static AnimalTypeEnum parse(int type) {
        AnimalTypeEnum[] types = AnimalTypeEnum.values();
        for (AnimalTypeEnum cm : types) {
            if (cm.code.equals(type)) {
                return cm;
            }
        }
        return null;
    }


    public static AnimalTypeEnum parseByCodeEn(String codeEn) {
        AnimalTypeEnum[] types = AnimalTypeEnum.values();
        for (AnimalTypeEnum cm : types) {
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

    public AnimalTypeEnum setCode(Integer code) {
        this.code = code;
        return this;
    }

    public AnimalTypeEnum setCodeEn(String codeEn) {
        this.codeEn = codeEn;
        return this;
    }

    public AnimalTypeEnum setCodeCn(String codeCn) {
        this.codeCn = codeCn;
        return this;
    }
}
