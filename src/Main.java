import functions.*;

public class Main {
    public static void main(String[] args) {
        TabulatedFunction func;
        
        func = new LinkedListTabulatedFunction(0, 3, new double[]{0, 1, 4, 9});
        // func = new ArrayTabulatedFunction(0, 3, new double[]{0, 1, 4, 9});

        //Проверка IllegalArgumentException
        try {
            func = new ArrayTabulatedFunction(3, 0, new double[]{0, 1, 4});
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано IllegalArgumentException: " + e.getMessage());
        }

        try {
            func = new ArrayTabulatedFunction(0, 3, new double[]{0});
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано IllegalArgumentException: " + e.getMessage());
        }

        //Проверка InappropriateFunctionPointException при добавлении существующей точки
        func = new ArrayTabulatedFunction(0, 3, new double[]{0, 1, 4, 9});
        try {
            func.addPoint(new FunctionPoint(1, 100));
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Поймано InappropriateFunctionPointException: " + e.getMessage());
        }

        //Добавление новой точки
        try {
            func.addPoint(new FunctionPoint(4, 16));
            System.out.println("Точка добавлена: " + func.getPointCopy(func.getPointsCount() - 1).getX());
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Ошибка при добавлении новой точки: " + e.getMessage());
        }

        //Проверка FunctionPointIndexOutOfBoundsException
        try {
            System.out.println(func.getPoint(10));
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("Поймано FunctionPointIndexOutOfBoundsException: " + e.getMessage());
        }

        //Проверка IllegalStateException при удалении точек
        try {
            func.deletePoint(0);
            func.deletePoint(0);
            func.deletePoint(0); // теперь точек меньше 3
        } catch (IllegalStateException e) {
            System.out.println("Поймано IllegalStateException: " + e.getMessage());
        }

        //Изменение Y точки
        try {
            func.setPointY(1, 10); // изменяем Y второй точки
            System.out.println("Y второй точки изменено на 10");
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("Ошибка при изменении Y: " + e.getMessage());
        }

        //Получение значения функции
        double val = func.getFunctionValue(2.5);
        System.out.println("Значение функции при x = 2.5: " + val);

        //Вывод текущего состояния функции
        System.out.println("\nТекущее состояние функции:");
        for (int i = 0; i < func.getPointsCount(); i++) {
            FunctionPoint p = func.getPointCopy(i);
            System.out.println("(" + p.getX() + ", " + p.getY() + ")");
        }
    }
}

