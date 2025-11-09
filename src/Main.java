import functions.*;

public class Main {
    public static void main(String[] args) {
        //TabulatedFunction func = new ArrayTabulatedFunction(0, 3, new double[]{0, 1, 4, 9});//
        TabulatedFunction func = new LinkedListTabulatedFunction(0, 3, new double[]{0, 1, 4, 9});

        //Проверка IllegalArgumentException при создании
        try {
            func = new ArrayTabulatedFunction(3, 0, new double[]{0, 1, 4}); // левая граница > правая
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано IllegalArgumentException: " + e.getMessage());
        }

        try {
            func = new ArrayTabulatedFunction(0, 3, new double[]{0}); // точек < 2
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано IllegalArgumentException: " + e.getMessage());
        }

        //Проверка InappropriateFunctionPointException при добавлении точки с уже существующим X
        func = new ArrayTabulatedFunction(0, 3, new double[]{0, 1, 4, 9});
        try {
            func.addPoint(new FunctionPoint(1, 100)); // X = 1 уже есть
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Поймано InappropriateFunctionPointException: " + e.getMessage());
        }

        //Проверка FunctionPointIndexOutOfBoundsException при доступе к несуществующему индексу
        try {
            System.out.println(func.getPoint(10));
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("Поймано FunctionPointIndexOutOfBoundsException: " + e.getMessage());
        }

        // --- Проверка IllegalStateException при удалении точки, если их < 3
        try {
            func.deletePoint(0);
            func.deletePoint(0);
            func.deletePoint(0);
        } catch (IllegalStateException e) {
            System.out.println("Поймано IllegalStateException: " + e.getMessage());
        }
    }
}
