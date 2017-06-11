package database.qin.xue.com.databasedemo;

import android.provider.BaseColumns;

/**
 * Created by xue.qin on 2017/5/27.
 *  SQL字段类
 *  final修饰符作用
 *  1.此类不能被继承，不希望此类被修改
 *  2.提供包访问权限，既可以更好的保护这些 字段（列名），又可以被SQLiteOpenHelper访问到
 */

public final class DemoContract {

    /*私有构造函数，不对外提供实例*/
    private DemoContract(){
    }
    /**
     * private 基础字段不直接对外提供
     */
    private interface DemoColumns extends BaseColumns{
        public static final String NAME = "name";
        public static final String GENDER = "gender";
    }
    /**
     * protected 提供包访问权限字段
     * 可以整合其他基础字段直接对外提供
     * (个人认为不用final提供包权限，完全是因为interface声明 final，java不允许，接口不能有实例了没了意义，不能用final)
     */
    protected interface DemoColumns2 extends DemoColumns{
        public static final String AGE = "age";
        public static final String COUNTRY ="country";
    }


}
