package com.juzhe.www.common.mvp_senior.factory;


import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;

/**
 * @author Administrator
 * @date 2018/10/16  14:29
 * @description 动态 工厂的实现类
 **/
public class MvpPresenterFactroyImpl<V extends IBaseView, P extends BasePresenter<V>> implements IMvpPresenterFactroy<V, P> {


    private final Class<P> mPresenterClass;

    private MvpPresenterFactroyImpl(Class<P> presenterClass) {
        this.mPresenterClass = presenterClass;
    }


    /**
     * 根据注解创建Presenter的工厂实现类
     *
     * @param aClass 需要创建Presenter的V层实现类
     * @param <V>    当前View实现的接口类型
     * @param <P>    当前要创建的Presenter类型
     * @return 工厂类
     */
    public static <V extends IBaseView, P extends BasePresenter<V>> MvpPresenterFactroyImpl creater(Class<?> aClass) {
        //拿到创建presenter的注解
        CreatePresenterAnnotation annotation = aClass.getAnnotation(CreatePresenterAnnotation.class);
        Class<P> currentPresenter = null;
        if (annotation != null) {
            //获取到具体的presenter
            currentPresenter = (Class<P>) annotation.value();

        }
        //传给有参构造
        return new MvpPresenterFactroyImpl(currentPresenter);
    }


    @Override
    public P createMvpPresenter() {
        try {
            return mPresenterClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("注解为空,请检查类上是否声明了@CreatePresenterAnnotation(xxx,class)注解");
        }
    }
}

