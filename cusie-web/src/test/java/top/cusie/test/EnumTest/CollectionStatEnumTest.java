package top.cusie.test.EnumTest;


import top.cusie.api.model.enums.CollectionStatEnum;

/**
 * @author Cusie
 * @date 2024/11/2
 */
public class CollectionStatEnumTest {
    public static void main(String[] args) {
        System.out.println(CollectionStatEnum.formCode(1).getCode());
        System.out.println("---------------------------------");
        System.out.println(CollectionStatEnum.COLLECTION);
    }

}
