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

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * if you want custom drawing of the graph, you can use this custom draw. 
 * As Input you get the Canvas
 * {@code
 * 		graphView.setCustomDraw(new CustomDraw() {
			public void onDraw(Canvas canvas, Paint paint, float graphheight, float border) {
				
			}
		});
 * }
 */
public interface CustomDraw {

	/**
	 * will be called from void GraphViewContentView.onDraw(Canvas canvas)
	 * @param canvas
	 * @param paint
	 */
	void MyDraw(Canvas canvas, Paint paint, float graphheight, float border);
}
