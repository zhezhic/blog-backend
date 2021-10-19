package xyz.zhezhi.common;

/**
 * @author zhezhi
 * @className: ElasticSearchIndex
 * @description: ElasticSearchIndex
 * @date 2021/10/17 下午7:10
 * @version：1.0
 */
public enum ElasticSearchIndex {
    BLOG("blog");
    private final String index;
    ElasticSearchIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return index;
    }
}
