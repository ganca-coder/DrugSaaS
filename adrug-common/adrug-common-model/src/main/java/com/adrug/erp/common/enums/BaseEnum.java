package com.adrug.erp.common.enums;

/**
 * 枚举基础接口。
 * <p>
 * 所有业务枚举统一实现本接口，约定：
 * <ul>
 *   <li>{@code code}：存储/传输值</li>
 *   <li>{@code name}：预留名称（默认取枚举常量名）</li>
 *   <li>{@code desc}：展示文案</li>
 * </ul>
 * 并提供 {@link #ofCode(Class, Object)} / {@link #ofName(Class, String)} 静态反查方法，
 * 供各枚举类统一委托，避免在每个枚举内重复遍历 {@code values()}。
 *
 * @param <T> code 的类型（通常为 Integer）
 */
public interface BaseEnum<T> {

    /**
     * 存储/传输值。
     */
    T getCode();

    /**
     * 预留名称。
     */
    String getName();

    /**
     * 展示文案。
     */
    String getDesc();

    /**
     * 按 code 反查枚举。
     *
     * @param enumType 枚举类型
     * @param code     code 值
     * @param <T>      code 类型
     * @param <E>      枚举类型
     * @return 匹配的枚举；code 为 null 或无匹配时返回 null
     */
    static <T, E extends Enum<E> & BaseEnum<T>> E ofCode(Class<E> enumType, T code) {
        if (code == null) {
            return null;
        }
        for (E item : enumType.getEnumConstants()) {
            T itemCode = item.getCode();
            if (itemCode != null && itemCode.equals(code)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 按 name 反查枚举。
     *
     * @param enumType 枚举类型
     * @param name     name 值
     * @param <T>      code 类型
     * @param <E>      枚举类型
     * @return 匹配的枚举；name 为 null 或无匹配时返回 null
     */
    static <T, E extends Enum<E> & BaseEnum<T>> E ofName(Class<E> enumType, String name) {
        if (name == null) {
            return null;
        }
        for (E item : enumType.getEnumConstants()) {
            String itemName = item.getName();
            if (itemName != null && itemName.equals(name)) {
                return item;
            }
        }
        return null;
    }
}
