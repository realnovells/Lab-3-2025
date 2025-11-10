package functions;

public interface TabulatedFunction {
    int getPointsCount();

    FunctionPoint getPoint(int index);

    FunctionPoint getPointCopy(int index);

    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    // Получение/установка координаты X точки
    double getPointX(int index);
    void setPointX(int index, double x) throws InappropriateFunctionPointException;

    double getPointY(int index);
    void setPointY(int index, double y);


    double getLeftDomainBorder();
    double getRightDomainBorder();

    double getFunctionValue(double x);

    // Удаление и добавление точки
    void deletePoint(int index);
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
}
