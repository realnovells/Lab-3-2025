package functions;

public class ArrayTabulatedFunction implements TabulatedFunction{
    private FunctionPoint[] points;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левой границе надо быть < правой");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Количество точек должно быть ≥ 2");
        }
        points = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, 0);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница должна быть < правой");
        }
        if (values.length < 2) {
            throw new IllegalArgumentException("Количество точек должно быть ≥ 2");
        }
        points = new FunctionPoint[values.length];
        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, values[i]);
        }
    }
    // Получение точки
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " вне границ массива точек");
        }

        return new FunctionPoint(points[index].getX(), points[index].getY());
    }

    public FunctionPoint[] getPoints() {

        return points;
    }
    //Задание 4
    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    public double getRightDomainBorder() {

        return points[points.length - 1].getX();
    }

    // вычисление значения функции
    public double getFunctionValue(double x) {
        double leftX = getLeftDomainBorder();
        double rightX = getRightDomainBorder();

        if (x < leftX || x > rightX) {
            return Double.NaN;
        }
        double epsilon = 1e-10;

        if (Math.abs(x - leftX) < epsilon) {
            return points[0].getY();
        }

        for (int i = 0; i < points.length - 1; i++) {
            double x1 = points[i].getX();
            double y1 = points[i].getY();
            double x2 = points[i + 1].getX();
            double y2 = points[i + 1].getY();

            if (x > x1 && x < x2) {
                // линейная интерполяция
                return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
            }
        }
        return Double.NaN;
    }

    //Задание 5
    public int getPointsCount() {
        return points.length;
    }

    public FunctionPoint getPointCopy(int index) {
        return new FunctionPoint(points[index].getX(), points[index].getY());
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }
        double x = point.getX();
        if ((index > 0 && x <= points[index - 1].getX()) ||
                (index < points.length - 1 && x >= points[index + 1].getX())) {
            throw new InappropriateFunctionPointException("Точка нарушает порядок X-координат");
        }
        points[index] = new FunctionPoint(point);
    }

    public double getPointX(int index) {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }
        return points[index].getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }
        if ((index > 0 && x <= points[index - 1].getX()) ||
                (index < points.length - 1 && x >= points[index + 1].getX())) {
            return;
        }
        points[index].setX(x);
    }

    public double getPointY(int index) {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }
        return points[index].getY();
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException(
                    "Индекс " + index + " вне границ массива точек"
            );
        }
        points[index].setY(y);
    }
    //Задание 6
    public void deletePoint(int index) {
        if (points.length < 3) {
            throw new IllegalStateException("Нельзя удалить точку, если их меньше трех");
        }
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }
        FunctionPoint[] newPoints = new FunctionPoint[points.length - 1];
        System.arraycopy(points, 0, newPoints, 0, index);
        System.arraycopy(points, index + 1, newPoints, index, points.length - index - 1);
        points = newPoints;
    }
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        double x = point.getX();
        for (FunctionPoint p : points) {
            if (Math.abs(p.getX() - point.getX()) < 1e-10) {
                throw new InappropriateFunctionPointException("Точка с таким X уже существует");
            }
        }
        int insertIndex = 0;
        while (insertIndex < points.length && points[insertIndex].getX() < x) {
            insertIndex++;
        }
        FunctionPoint[] newPoints = new FunctionPoint[points.length + 1];
        System.arraycopy(points, 0, newPoints, 0, insertIndex);
        newPoints[insertIndex] = new FunctionPoint(point);
        System.arraycopy(points, insertIndex, newPoints, insertIndex + 1, points.length - insertIndex);
        points = newPoints;
    }
}