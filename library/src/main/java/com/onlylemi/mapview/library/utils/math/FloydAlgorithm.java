package com.onlylemi.mapview.library.utils.math;

import java.util.ArrayList;
import java.util.List;

/**
 * FloydAlgorithm
 * 算法的基本思想为:
 * 通过Floyd计算图G=(V,E)中各个顶点的最短路径时，需要引入两个矩阵，
 * 矩阵S中的元素a[i][j]表示顶点i(第i个顶点)到顶点j(第j个顶点)的距离。
 * 矩阵P中的元素b[i][j]，表示顶点i到顶点j经过了b[i][j]记录的值所表示的顶点。
 * 假设图G中顶点个数为N，则需要对矩阵D和矩阵P进行N次更新。
 * 初始时，矩阵D中顶点a[i][j]的距离为顶点i到顶点j的权值；
 * 如果i和j不相邻，则a[i][j]=∞，矩阵P的值为顶点b[i][j]的j的值。
 * 接下来开始，对矩阵D进行N次更新。
 * 第1次更新时，如果”a[i][j]的距离” > “a[i][0]+a[0][j]”(a[i][0]+a[0][j]表示”i与j之间经过第1个顶点的距离”)，
 * 则更新a[i][j]为”a[i][0]+a[0][j]”,更新b[i][j]=b[i][0]。
 * 同理，第k次更新时，如果”a[i][j]的距离” > “a[i][k-1]+a[k-1][j]”，
 * 则更新a[i][j]为”a[i][k-1]+a[k-1][j]”,b[i][j]=b[i][k-1]。
 * 更新N次之后，操作完成！
 * @author: onlylemi
 */
public final class FloydAlgorithm {

    private static final int INF = Integer.MAX_VALUE;
    private float[][] dist;

    // the shortest path from i to j
    private int[][] path;
    private List<Integer> result;

    public static FloydAlgorithm getInstance() {
        return FloydAlgorithmHolder.instance;
    }

    private static class FloydAlgorithmHolder {
        private static FloydAlgorithm instance = new FloydAlgorithm();
    }

    private void init(float[][] matrix) {
        dist = null;
        path = null;
        result = new ArrayList<>(); //存放从起始节点到结束节点之间的所需要经过的节点索引
        //初始化D矩阵和P矩阵
        this.dist = new float[matrix.length][matrix.length];
        this.path = new int[matrix.length][matrix.length];
    }

    /**
     * the shortest between begin to end
     *
     * @param begin
     * @param end
     * @param matrix
     */
    public List<Integer> findCheapestPath(int begin, int end, float[][] matrix) {
        init(matrix);

        floyd(matrix);
        result.add(begin);
        findPath(begin, end);
        result.add(end);

        return result;
    }

    private void findPath(int i, int j) {
        int k = path[i][j];
        if (k == -1)
            return;
        findPath(i, k); // recursion
        result.add(k);
        findPath(k, j);
    }

    private void floyd(float[][] matrix) {
        int size = matrix.length;
        // initialize dist and path
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                path[i][j] = -1;
                dist[i][j] = matrix[i][j];
            }
        }
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = k;
                    }
                }
            }
        }

    }

}
