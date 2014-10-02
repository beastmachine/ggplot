package org.beastmachine.util;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VisualUtilities {

  public static void waitToClose(final Window f) {
    final Object lock = new Object();
  
    Thread t = new Thread() {
      public void run() {
        synchronized(lock) {
          while (f.isVisible())
            try {
              lock.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
        }
      }
    };
    t.start();
  
    f.addWindowListener(new WindowAdapter() {
  
      @Override
      public void windowClosing(WindowEvent arg0) {
        synchronized (lock) {
          f.setVisible(false);
          lock.notify();
        }
      }
  
    });
  
    try {
      t.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
