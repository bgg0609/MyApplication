package bgg.com.myapplication.common.persenter;

/**
 * 公共的persenterview接口，定义公共方法，提供activity和persenter交互
 * Created by dell on 2016/9/6.
 */
public interface BasePersenterView {
    /**
     * 显示信息
     * @param message message
     */
    void showMessage(String message);

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 关闭
     */
    void closeLoading();

    /**
     * 销毁方法
     */
    void onDestory();
}
