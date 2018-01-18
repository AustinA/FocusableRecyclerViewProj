package me.duhblea.focusablerecyclerview.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import me.duhblea.focusablerecyclerview.managers.IItemDetailsProvider;
import me.duhblea.focusablerecyclervivew.R;

/**
 * Provides details for an ECEFPoint object.
 */
public class ECEFDetailsProvider implements IItemDetailsProvider<ECEFPoint> {
    private Context ctx;
    private View visibleView;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private AtomicInteger atomicInteger = new AtomicInteger(2);

    private ScheduledExecutorService executorService;

    ECEFDetailsProvider(Context ctx)
    {
        if (ctx == null)
        {
            throw new IllegalArgumentException("Context cannot be null!");
        }

        this.ctx = ctx;
    }

    @Override
    public View getView(ECEFPoint ecefPoint) {
        if (ecefPoint != null) {
            LayoutInflater inflater = LayoutInflater.from(ctx);

            if (inflater != null) {
                View view = inflater.inflate(R.layout.selected_item_view, null);

                if (view != null) {

                    TextView x = view.findViewById(R.id.xlabel);
                    TextView y = view.findViewById(R.id.ylabel);
                    TextView z = view.findViewById(R.id.zlabel);

                    x.setText(String.valueOf(ecefPoint.getX()));
                    y.setText(String.valueOf(ecefPoint.getY()));
                    z.setText(String.valueOf(ecefPoint.getZ()));

                    visibleView = view;
                }
            }
        }

        return visibleView;
    }

    @Override
    public void onDetailsProvided() {

        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (visibleView != null) {
                            TextView a = visibleView.findViewById(R.id.asyncLabel);
                            a.setText(String.valueOf(atomicInteger.incrementAndGet()));
                        }
                    }
                });
            }
        }, 0, 250, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDetailsRemoved() {
        executorService.shutdownNow();
    }
}
