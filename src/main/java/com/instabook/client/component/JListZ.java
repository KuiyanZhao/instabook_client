package com.instabook.client.component;

import javax.swing.*;
import java.awt.*;

public class JListZ<E> extends JList<E> {

    @Override
    public int locationToIndex(Point location) {
        int index = super.locationToIndex(location);
        if (index != -1 && !getCellBounds(index, index).contains(location)) {
            return -1;
        } else {
            return index;
        }
    }

}
