package com.example.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CanvasDrawer {
    private static final int PIXEL_SIZE = 20;
    private GraphicsContext gc;
    private LineDrawer lineDrawer;
    private int startX = -1;
    private int startY = -1;

    public CanvasDrawer(GraphicsContext gc) {
        this.gc = gc;
        this.lineDrawer = new LineDrawer(gc);
        setupInteractiveDrawing();
    }

    public void draw() {
        drawPixelField();
        drawDDAFigure();
        drawBresenhamFigure();
        drawCanvasLine();
    }

    private void setupInteractiveDrawing() {
        gc.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int cellX = (int) (event.getX() / PIXEL_SIZE);
            int cellY = (int) (event.getY() / PIXEL_SIZE);

            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            draw(); // Перерисовываем все

            drawText("X: " + cellX + ", Y: " + cellY, cellX, cellY, 16);

            if (startX == -1 && startY == -1) {
                startX = cellX;
                startY = cellY;
            } else {
                lineDrawer.drawLineBresenham(startX, startY, cellX, cellY);
                startX = -1;
                startY = -1;
            }
            highlightPixel(cellX, cellY);
        });
    }

    private void drawText(String text, int x, int y, int fontSize) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", fontSize));
        gc.fillText(text, x * PIXEL_SIZE, y * PIXEL_SIZE);
    }

    private void highlightPixel(int x, int y) {
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(2);
        gc.strokeRect(x * PIXEL_SIZE, y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }

    private void drawCanvasLine() {
        drawText("Реализация отрезка алгоритмом Брезенхама", 12, 23, 20);
        gc.setStroke(Color.RED);
        gc.setLineWidth(4);
        gc.strokeLine(23 * PIXEL_SIZE, 0 * PIXEL_SIZE, 23 * PIXEL_SIZE, 21 * PIXEL_SIZE);
        gc.strokeLine(0 * PIXEL_SIZE, 21 * PIXEL_SIZE, 48 * PIXEL_SIZE, 21 * PIXEL_SIZE);
    }

    private void drawPixelField() {
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);
        for (int x = 0; x < gc.getCanvas().getWidth(); x += PIXEL_SIZE) {
            for (int y = 0; y < gc.getCanvas().getHeight(); y += PIXEL_SIZE) {
                gc.strokeRect(x, y, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    private void drawDDAFigure() {
        drawText("Алгоритм ЦДА", 8, 20, 20);
        lineDrawer.drawDDAFigure();
    }

    private void drawBresenhamFigure() {
        drawText("Алгоритм Брезенхама", 29, 20, 20);
        lineDrawer.drawBresenhamFigure();
    }
}
