package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction {
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        this(); // вызываем конструктор, создающий пустой список
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левой границе надо быть < правой");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Количество точек должно быть ≥ 2");
        }
        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            addNodeToTail(new FunctionPoint(leftX + i * step, 0));
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        this();
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница должна быть < правой");
        }
        if (values.length < 2) {
            throw new IllegalArgumentException("Количество точек должно быть ≥ 2");
        }
        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++) {
            addNodeToTail(new FunctionPoint(leftX + i * step, values[i]));
        }
    }

    // Класс узла списка
    private static class FunctionNode {
        private FunctionPoint point; // точки x y
        private FunctionNode next;
        private FunctionNode prev;

        public FunctionNode(FunctionPoint point) {
            this.point = point;
        }
    }

    private FunctionNode head; // голова списка
    private int size;

    public LinkedListTabulatedFunction() {
        head = new FunctionNode(null);
        head.next = head;
        head.prev = head;
        size = 0;
    }

    // Получение узла по индексу
    private FunctionNode getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }
        //Оптимизация
        FunctionNode current;
        if (index < size / 2) {
            current = head.next;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = head.prev;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    // Добавл узла в конец списка
    public FunctionNode addNodeToTail(FunctionPoint point) {
        FunctionNode node = new FunctionNode(point);
        node.prev = head.prev;
        node.next = head;
        head.prev.next = node;
        head.prev = node;
        size++;
        return node;
    }

    // Добавл узла по индексу
    public FunctionNode addNodeByIndex(int index, FunctionPoint point) {
        if (index < 0 || index > size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }

        FunctionNode nextNode;
        if (index == size)
            nextNode = head;
        else
            nextNode = getNodeByIndex(index);
        FunctionNode prevNode = nextNode.prev;

        FunctionNode node = new FunctionNode(point);
        node.next = nextNode;
        node.prev = prevNode;
        prevNode.next = node;
        nextNode.prev = node;

        size++;
        return node;
    }

    // Удаление узла по индексу
    public FunctionNode deleteNodeByIndex(int index) {
        if (size < 3) {
            throw new IllegalStateException("Ошибка: меньше 3 точек");
        }
        FunctionNode node = getNodeByIndex(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node;
    }

    // Получение точки по индексу
    public FunctionPoint getPoint(int index) {
        FunctionPoint p = getNodeByIndex(index).point;
        return new FunctionPoint(p.getX(), p.getY());
    }


    // Получение количества точек
    public int getPointsCount() {
        return size;
    }

    public void setPointY(int index, double y) {
        getNodeByIndex(index).point.setY(y);
    }

    // ===== Добавленные методы для задания 5 =====

    public FunctionPoint getPointCopy(int index) {
        FunctionPoint p = getPoint(index);
        return new FunctionPoint(p.getX(), p.getY());
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }
        double x = point.getX();
        if ((index > 0 && x <= getPointX(index - 1)) ||
                (index < size - 1 && x >= getPointX(index + 1))) {
            throw new InappropriateFunctionPointException("Точка нарушает порядок X-координат");
        }
        getNodeByIndex(index).point = new FunctionPoint(point);
    }

    public double getPointX(int index) {
        return getPoint(index).getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Индекс вне границ");
        }
        if ((index > 0 && x <= getPointX(index - 1)) ||
                (index < size - 1 && x >= getPointX(index + 1))) {
            throw new InappropriateFunctionPointException("Нарушение порядка X-координат");
        }
        getNodeByIndex(index).point.setX(x);
    }

    public double getPointY(int index) {
        return getPoint(index).getY();
    }

    public double getLeftDomainBorder() {
        return getPointX(0);
    }

    public double getRightDomainBorder() {
        return getPointX(size - 1);
    }

    public double getFunctionValue(double x) {
        double leftX = getLeftDomainBorder();
        double rightX = getRightDomainBorder();

        if (x < leftX || x > rightX) {
            return Double.NaN;
        }

        double epsilon = 1e-10; // машинный эпсилон
        //если x почти равен левому краю
        if (Math.abs(x - leftX) < epsilon) {
            return head.next.point.getY();
        }
        if (Math.abs(x - rightX) < epsilon) {
            return head.prev.point.getY();
        }

        FunctionNode current = head.next;
        while (current.next != head) {
            double x1 = current.point.getX();
            double y1 = current.point.getY();
            double x2 = current.next.point.getX();
            double y2 = current.next.point.getY();

            if (x > x1 && x < x2) {
                return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
            }
            current = current.next;
        }
        return Double.NaN;
    }

    public void deletePoint(int index) {
        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        double x = point.getX();
        FunctionNode current = head.next;
        int index = 0;
        while (current != head && current.point.getX() < x) {
            if (Math.abs(current.point.getX() - x) < 1e-10) {
                throw new InappropriateFunctionPointException("Точка с таким X уже существует");
            }
            current = current.next;
            index++;
        }
        addNodeByIndex(index, new FunctionPoint(point));
    }
}
