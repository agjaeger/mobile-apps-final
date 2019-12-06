package com.ualr.idlegame.fragments.interfaces;

public interface TabFragment {
    public void onTick ();

    public boolean isActive ();
    public void activate ();
    public void deactivate();
}
