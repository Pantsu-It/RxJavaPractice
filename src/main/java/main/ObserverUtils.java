package main;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class ObserverUtils {
    public static Observer<Integer> integerObserver() {
        return new Observer<Integer>() {
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe：");
            }

            public void onNext(@NonNull Integer integer) {
                System.out.println("onNext: " + integer);
            }

            public void onError(@NonNull Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            public void onComplete() {
                System.out.println("onComplete：");
            }
        };
    }

    public static Observer<String> stringObserver() {
        return new Observer<String>() {
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            public void onNext(@NonNull String s) {
                System.out.println("onNext: " + s);
            }

            public void onError(@NonNull Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            public void onComplete() {
                System.out.println("onComplete");
            }
        };
    }
}
