package cz.muni.fi.pv260.assigment3.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class IdentityDisharmoniesCheck extends AbstractCheck {

    private int maxMethodLoc = 1000;
    private int maxMethodComplexity = 120;
    private int maxMethodNesting = 3;
    private int maxMethodVariablesCount = 10;

    private boolean inMethod = false;

    private int nestingDepth = 0;
    private int maxNestingDepth = 0;

    private int cyclomaticComplexity = 0;
    private int variablesCounter = 0;

    private int methodLoc = 0;

    public int[] getDefaultTokens() {
        return new int[]{
                TokenTypes.METHOD_DEF,
                TokenTypes.LITERAL_FOR,
                TokenTypes.LITERAL_IF,
                TokenTypes.LITERAL_WHILE,
                TokenTypes.LITERAL_SWITCH,
                TokenTypes.LITERAL_BREAK,
                TokenTypes.LITERAL_TRY,
                TokenTypes.LITERAL_CATCH,
                TokenTypes.LITERAL_FINALLY,
                TokenTypes.LAND,
                TokenTypes.LOR,
                TokenTypes.SLIST,
                TokenTypes.RCURLY,
                TokenTypes.CASE_GROUP,
                TokenTypes.VARIABLE_DEF,
                TokenTypes.SEMI
        };
    }

    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF) {
            inMethod = true;
        }
        processBracketsNesting(ast);
        processCyclomaticSymbols(ast);
        processLocalVariables(ast);
        processLoc(ast);
    }


    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF) {
            inMethod = false;
            compareProperties(ast);
            resetProperties();
        }
    }

    /**
     * Counts each local variable definition as + 1 variable
     */
    private void processLocalVariables(DetailAST ast) {
        if (!inMethod) {
            return;
        }
        if (ast.getType() == TokenTypes.VARIABLE_DEF) {
            variablesCounter++;
        }
    }

    /**
     * Nesting is always + 1 because i count method block also as a nested level
     */
    private void processBracketsNesting(DetailAST ast) {
        if (!inMethod) {
            return;
        }

        if (ast.getType() == TokenTypes.RCURLY || ast.getType() == TokenTypes.LITERAL_BREAK) {
            nestingDepth--;
        } else if (ast.getType() == TokenTypes.SLIST) {
            nestingDepth++;
        }

        if (nestingDepth > maxNestingDepth) {
            maxNestingDepth = nestingDepth;
        }
    }

    /**
     * Counts all semicolon statements as a logical line of code,
     * also statements which do not require semicolons such as if, for, while etc.
     * also counts end of curly brackets as loc
     *
     */
    private void processLoc(DetailAST ast) {
        if (!inMethod) {
            return;
        }

        if (ast.getType() == TokenTypes.LITERAL_BREAK
                || ast.getType() == TokenTypes.LITERAL_IF
                || ast.getType() == TokenTypes.LITERAL_FOR
                || ast.getType() == TokenTypes.LITERAL_WHILE
                || ast.getType() == TokenTypes.LITERAL_DO
                || ast.getType() == TokenTypes.LITERAL_CASE
                || ast.getType() == TokenTypes.LITERAL_SWITCH
                || ast.getType() == TokenTypes.LITERAL_TRY
                || ast.getType() == TokenTypes.LITERAL_CATCH
                || ast.getType() == TokenTypes.LITERAL_FINALLY
                || ast.getType() == TokenTypes.SEMI
                || ast.getType() == TokenTypes.RCURLY
        ) {
            methodLoc++;
        }
    }

    /**
     * Counter based on https://www.theserverside.com/feature/How-to-calculate-McCabe-cyclomatic-complexity-in-Java
     */
    private void processCyclomaticSymbols(DetailAST ast) {
        if (!inMethod) {
            return;
        }

        if (ast.getType() == TokenTypes.LITERAL_FOR
                || ast.getType() == TokenTypes.LITERAL_WHILE
                || ast.getType() == TokenTypes.LITERAL_DO
                || ast.getType() == TokenTypes.LITERAL_IF
                || ast.getType() == TokenTypes.LAND
                || ast.getType() == TokenTypes.LOR
                || ast.getType() == TokenTypes.CASE_GROUP) {

            cyclomaticComplexity++;
        }
    }

    private void compareProperties(DetailAST ast) {
        if (cyclomaticComplexity >= maxMethodComplexity && maxNestingDepth >= maxMethodNesting
                && variablesCounter >= maxMethodVariablesCount && methodLoc >= maxMethodLoc) {
            log(ast.getLineNo(), "Method is a brain method");
        }
        log(ast.getLineNo(), "depth: " + maxNestingDepth);
        log(ast.getLineNo(), "var: " + variablesCounter);
        log(ast.getLineNo(), "loc: " + methodLoc);
        log(ast.getLineNo(), "ccompl: " + cyclomaticComplexity);
    }

    private void resetProperties() {
        nestingDepth = 0;
        maxNestingDepth = 0;

        methodLoc = 0;
        cyclomaticComplexity = 0;
        variablesCounter = 0;
    }

    // region Properties setters

    public void setMaxMethodLoc(int maxMethodLoc) {
        this.maxMethodLoc = maxMethodLoc;
    }

    public void setMaxMethodComplexity(int maxMethodComplexity) {
        this.maxMethodComplexity = maxMethodComplexity;
    }

    public void setMaxMethodNesting(int maxMethodNesting) {
        this.maxMethodNesting = maxMethodNesting;
    }

    public void setMaxMethodVariablesCount(int maxMethodVariablesCount) {
        this.maxMethodVariablesCount = maxMethodVariablesCount;
    }

    // endregion
}
