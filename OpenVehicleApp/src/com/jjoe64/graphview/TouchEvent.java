/**
 * This file is part of GraphView.
 *
 * GraphView is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GraphView is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GraphView.  If not, see <http://www.gnu.org/licenses/lgpl.html>.
 *
 * Copyright Jonas Gehring
 */

package com.jjoe64.graphview;

import android.view.MotionEvent;

/**
 * if you want to handle a touch event in the graph view, you can use this touch event.
 * {@code
 * 		graphView.setTouchEvent(new TouchEvent() {
			public boolean onTouchEvent(MotionEvent event) {
			
				//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/167-urok-102-touch-obrabotka-kasanija.html
				
				float x;
				float y;
				
				x = event.getX();
				y = event.getY();
			    
			    switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			    	//TO DO
			    	return true;
			    case MotionEvent.ACTION_MOVE:
			    	//TO DO
			    	return true;
			    case MotionEvent.ACTION_UP:
			    	//TO DO
			    	return true;
			    case MotionEvent.ACTION_CANCEL:  
			    	//TO DO
			    	return false;
			    }
			    
				return false; // let graphview to handle a touch event
			}
		});
 * }
 */
public interface TouchEvent {

    /**
     * To handle GraphView touch screen motion events.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
	boolean onTouchEvent(MotionEvent event);
}
