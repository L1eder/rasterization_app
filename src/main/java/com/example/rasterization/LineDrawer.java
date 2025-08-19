package com.example.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineDrawer {
    private static final int PIXEL_SIZE = 20;
    private GraphicsContext gc;

    public LineDrawer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void drawDDAFigure() {
        // Метод ЦДА, рисуем кубик
        drawLineDDA(4, 5, 11, 1);
        drawLineDDA(11, 1, 18, 5);
        drawLineDDA(4, 5, 11, 9);
        drawLineDDA(11, 9, 18, 5);
        drawLineDDA(4, 5, 4, 13);
        drawLineDDA(4, 13, 11, 17);
        drawLineDDA(11, 9, 11, 17);
        drawLineDDA(18, 5, 18, 13);
        drawLineDDA(11, 17, 18, 13);
    }

    public void drawBresenhamFigure() {
        // Метод Брезенхама, рисуем кубик
        drawLineBresenham(27, 5, 34, 1);
        drawLineBresenham(34, 1, 41, 5);
        drawLineBresenham(27, 5, 34, 9);
        drawLineBresenham(34, 9, 41, 5);
        drawLineBresenham(27, 5, 27, 13);
        drawLineBresenham(27, 13, 34, 17);
        drawLineBresenham(34, 9, 34, 17);
        drawLineBresenham(41, 5, 41, 13);
        drawLineBresenham(34, 17, 41, 13);
    }

    public void drawThinLine(int x0, int y0, int x1, int y1) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(x0 * PIXEL_SIZE + PIXEL_SIZE / 2, y0 * PIXEL_SIZE + PIXEL_SIZE / 2, x1 * PIXEL_SIZE + PIXEL_SIZE / 2, y1 * PIXEL_SIZE + PIXEL_SIZE / 2);
    }

    private void drawLineDDA(int x0, int y0, int x1, int y1) {
        x0 *= PIXEL_SIZE;
        y0 *= PIXEL_SIZE;
        x1 *= PIXEL_SIZE;
        y1 *= PIXEL_SIZE;

        int dx = x1 - x0;
        int dy = y1 - y0;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x0;
        float y = y0;

        for (int i = 0; i <= steps; i++) {
            int cellX = Math.round(x / PIXEL_SIZE);
            int cellY = Math.round(y / PIXEL_SIZE);
            gc.fillRect(cellX * PIXEL_SIZE, cellY * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            x += xIncrement;
            y += yIncrement;
        }
    }

    void drawLineBresenham(int x0, int y0, int x1, int y1) {
        x0 *= PIXEL_SIZE;
        y0 *= PIXEL_SIZE;
        x1 *= PIXEL_SIZE;
        y1 *= PIXEL_SIZE;

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            gc.fillRect(x0, y0, PIXEL_SIZE, PIXEL_SIZE);

            if (x0 == x1 && y0 == y1) break;

            int e2 = err * 2;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx * PIXEL_SIZE;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy * PIXEL_SIZE;
            }
        }
    }
}
