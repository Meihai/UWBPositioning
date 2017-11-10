package com.onlylemi.mapview.agorithm.trilateration;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Created by admin on 2017/11/9.
 */

public class LinearLeastSquaresSolver {
    protected final TrilaterationFunction function;

    public LinearLeastSquaresSolver(TrilaterationFunction function) {
        this.function = function;
    }

    public RealVector solve(boolean debugInfo) {
        int numberOfPositions = function.getPositions().length;
        int positionDimension = function.getPositions()[0].length;

        double[][] Ad = new double[numberOfPositions - 1][positionDimension];

        // TODO: which reference position should be used?  currently using postion and distance in index 0.

        for (int i = 1; i < numberOfPositions; i++) {
            double[] Adi = new double[positionDimension];
            for (int j = 0; j < positionDimension; j++) {
                Adi[j] = function.getPositions()[i][j] - function.getPositions()[0][j];
            }
            Ad[i - 1] = Adi;
        }
        if (debugInfo) {
            System.out.println(new Array2DRowRealMatrix(Ad));
        }

        // reference point is function.getPositions()[0], with distance function.getDistances()[0]
        double referenceDistance = function.getDistances()[0];
        double r0squared = referenceDistance * referenceDistance;
        double[] bd = new double[numberOfPositions - 1];
        for (int i = 1; i < numberOfPositions; i++) {
            double ri = function.getDistances()[i];
            double risquared = ri * ri;

            // find distance between ri and r0
            double di0squared = 0;
            for (int j = 0; j < positionDimension; j++) {
                double dij0j = function.getPositions()[i][j] - function.getPositions()[0][j];
                di0squared += dij0j * dij0j;
            }
            bd[i - 1] = 0.5 * (r0squared - risquared + di0squared);
        }
        if (debugInfo) {
            System.out.println(new ArrayRealVector(bd));
        }

        RealMatrix A = new Array2DRowRealMatrix(Ad, false);
        RealVector b = new ArrayRealVector(bd, false);
        DecompositionSolver solver = new QRDecomposition(A).getSolver();
        RealVector x;
        if(!solver.isNonSingular()) {
            // bummer...
            x = new ArrayRealVector(new double[positionDimension]);
        } else {
            x = solver.solve(b);
        }

        return x.add(new ArrayRealVector(function.getPositions()[0]));
    }

    public RealVector solve() {
        return solve(false);
    }
}
