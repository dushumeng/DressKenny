package com.fcdream.dress.kenny.bo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by shmdu on 2017/9/2.
 */

public interface JsonCreator<T> {


    /**
     * 从json数据创建一个实体对象
     *
     * @param data 数据
     * @return 实体对象实例
     */
    public T createFromJSONObject(JSONObject data) throws JSONException;

    /**
     * Entity实体建造器
     *
     * @author dushumeng
     */
    public static interface EntityJsonCreator extends JsonCreator<Entity> {

    }

    /**
     * 单个字符串
     *
     * @author dushumeng
     */
    public static interface StringJsonCreator extends JsonCreator<String> {

    }

    /**
     * 单个数字
     *
     * @author dushumeng
     */
    public static interface LongJsonCreator extends JsonCreator<Long> {

    }

    /**
     * 单个布尔型
     *
     * @author dushumeng
     */
    public static interface BooleanJsonCreator extends JsonCreator<Boolean> {

    }

    /**
     * Entity实体列表建造器
     *
     * @author dushumeng
     */
    public static interface EntityListJsonCreator extends JsonCreator<List<? extends Entity>> {

    }

    /**
     * Entity实体列表建造器
     *
     * @author dushumeng
     */
    public static interface PageListJsonCreator extends JsonCreator<PageList> {

    }

    public class PageList {
        private long sum;
        private List<? extends Entity> list;

        public PageList() {
            super();
        }

        public PageList(long sum, List<? extends Entity> list) {
            super();
            this.sum = sum;
            this.list = list;
        }

        public long getSum() {
            return sum;
        }

        public void setSum(long sum) {
            this.sum = sum;
        }

        public List<? extends Entity> getList() {
            return list;
        }

        public void setList(List<? extends Entity> list) {
            this.list = list;
        }
    }

}
