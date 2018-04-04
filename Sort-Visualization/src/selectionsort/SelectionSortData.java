package selectionsort;

public class SelectionSortData {

    private int[] numbers;

    public int orderedIndex = -1;           // [0...orderedIndex) 鏄湁搴忕殑
    public int currentCompareIndex = -1;    // 褰撳墠姝ｅ湪姣旇緝鐨勫厓绱犵储寮�
    public int currentMinIndex = -1;        // 褰撳墠鎵惧埌鐨勬渶灏忓厓绱犵殑绱㈠紩

    public SelectionSortData(int N, int randomBound){

        numbers = new int[N];

        for( int i = 0 ; i < N ; i ++)
            numbers[i] = (int)(Math.random()*randomBound) + 1;
    }

    public int N(){
        return numbers.length;
    }

    public int get(int index){
        if( index < 0 || index >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        return numbers[index];
    }

    public void swap(int i, int j) {

        if( i < 0 || i >= numbers.length || j < 0 || j >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
    }
}
