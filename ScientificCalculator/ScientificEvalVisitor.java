import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

 public class ScientificEvalVisitor
		 extends ScientificCalcBaseVisitor<Double> {

	 Map<String, Double> memory = new HashMap<>();


	@Override
	public Double visitNumber(
			 ScientificCalcParser.NumberContext ctx) {

		 return Double.parseDouble(
			 ctx.NUMBER().getText()
		 );
	 }

	@Override
	public Double visitAddSub(
			ScientificCalcParser.AddSubContext ctx) {

		double left = visit(ctx.expr(0));
		double right = visit(ctx.expr(1));

		if (ctx.op.getType() == ScientificCalcParser.ADD) {
			return left + right;
	}

		return left - right;
 }

	@Override
	public Double visitMulDiv(
        	        ScientificCalcParser.MulDivContext ctx) {

		double left = visit(ctx.expr(0));
		double right = visit(ctx.expr(1));

        	if (ctx.op.getType() == ScientificCalcParser.MUL) {
        		return left * right;        	}

		if (right ==0){
			throw new RuntimeException("No se puede dividir entre cero");
		}

        	return left / right;
	}

	@Override
	public Double visitParens(
			ScientificCalcParser.ParensContext ctx) {

		return visit(ctx.expr());
	 }

	@Override
	public Double visitPrintExpr(
			ScientificCalcParser.PrintExprContext ctx) {

		double value = visit(ctx.expr());

		System.out.println(value);

		return value;
	}


	@Override
	public Double visitAssign(
			ScientificCalcParser.AssignContext ctx) {

		String id = ctx.ID().getText();

    		double value = visit(ctx.expr());

    		memory.put(id, value);

    		return value;
}

	@Override
	public Double visitId(
        		ScientificCalcParser.IdContext ctx) {

		String id = ctx.ID().getText();

    		if (memory.containsKey(id)) {
			return memory.get(id);
    	}

    		System.err.println(
        		"Variable no definida: " + id
    	);

    	return 0.0;
}

	@Override
	public Double visitPower(
			ScientificCalcParser.PowerContext ctx) {

		double base = visit(ctx.expr(0));
		double exponent = visit(ctx.expr(1));

		return Math.pow(base, exponent);
}

	@Override
	public Double visitFunctionCall(
			ScientificCalcParser.FunctionCallContext ctx) {

		String function = ctx.function().getText();

		double value1 = visit(ctx.expr(0));

		if (ctx.expr().size() == 2) {

			double value2 = visit(ctx.expr(1));

			switch (function) {

				case "pow":
					return Math.pow(value1, value2);

				case "max":
					return Math.max(value1, value2);

				case "min":
					return Math.min(value1, value2);

				default:
					throw new RuntimeException(
						"Funcion desconocida: " + function
					);
			}
		}

		switch (function) {

			case "sin":
				return Math.sin(value1);

			case "cos":
				return Math.cos(value1);

			case "tan":
				return Math.tan(value1);

			case "sqrt":
				return Math.sqrt(value1);

			case "log":
				return Math.log10(value1);

			case "ln":
				return Math.log(value1);

			case "abs":
				return Math.abs(value1);

			case "exp":
				return Math.exp(value1);

			case "asin":
				return Math.asin(value1);

			case "acos":
				return Math.acos(value1);

			case "atan":
				return Math.atan(value1);

			case "floor":
				return Math.floor(value1);

			case "ceil":
				return Math.ceil(value1);

			default:
				throw new RuntimeException(
					"Funcion desconocida: " + function
				);
    }
}

	@Override
	public Double visitUnary(
			ScientificCalcParser.UnaryContext ctx) {

		double value = visit(ctx.expr());

		if (ctx.op.getText().equals("-")) {
			return -value;
		}

		return value;
	}

	@Override
	public Double visitConstantExpr(
			ScientificCalcParser.ConstantExprContext ctx) {

		String constant =
			ctx.constant().getText();

		if (constant.equals("pi")) {
			return Math.PI;
		}

		if (constant.equals("e")) {
			return Math.E;
		}

		return 0.0;
}

	@Override
	public Double visitClear(
			ScientificCalcParser.ClearContext ctx) {

		memory.clear();

		System.out.println(
			"Memoria elimanada."
		);

		return 0.0;
}

	@Override
	public Double visitShowVars(
			ScientificCalcParser.ShowVarsContext ctx) {

		if (memory.isEmpty()) {
			System.out.println(
				"No hay variables definidas."
			);
			return 0.0;
		}

		for (Map.Entry<String, Double> entry :
				memory.entrySet()) {

			System.out.println(
				entry.getKey()
				+ " = "
				+ entry.getValue()
			);
		}

		return 0.0;
}

	@Override
	public Double visitPlotExpr(
        		ScientificCalcParser.PlotExprContext ctx) {

    		double xmin = visit(ctx.expr(1));
    		double xmax = visit(ctx.expr(2));
			double ymin = visit(ctx.expr(3));
			double ymax = visit(ctx.expr(4));

    		int samples = 800;

    		List<Double> xs =
            		new ArrayList<>();

    		List<Double> ys =
            		new ArrayList<>();

    		for (int i = 0;
         		i < samples;
         		i++) {

        	double x =
                	xmin
                	+ i * (xmax - xmin)
                	/ (samples - 1);

        	memory.put("x", x);

        	double y =
                	visit(ctx.expr(0));

        	if (Double.isFinite(y)) {
		xs.add(x);
        	ys.add(y);
    	}
}
    	new PlotWindow(xs, ys, ys, ymin, ymax);

    return 0.0;
}
	@Override
	public Double visitPlotTwoExpr(
			ScientificCalcParser.PlotTwoExprContext ctx) {

		double xmin = visit(ctx.expr(2));
		double xmax = visit(ctx.expr(3));

		int samples = 800;

		List<Double> xs = new ArrayList<>();
		List<Double> ys1 = new ArrayList<>();
		List<Double> ys2 = new ArrayList<>();

		for (int i = 0; i < samples; i++) {

			double x =
				xmin
				+ i * (xmax - xmin)
				/ (samples - 1);

			memory.put("x", x);

			double y1 = visit(ctx.expr(0));
			double y2 = visit(ctx.expr(1));

			xs.add(x);
			ys1.add(y1);
			ys2.add(y2);
		}

		new PlotWindow(
			xs,
			ys1,
			ys2,
			-1,
			1
		);

		return 0.0;
	}
}
