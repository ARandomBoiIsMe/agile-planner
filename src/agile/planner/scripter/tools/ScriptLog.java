package agile.planner.scripter.tools;

import agile.planner.scripter.*;

public class ScriptLog {

    private StringBuilder sb;

    public ScriptLog() {
        sb = new StringBuilder();
        sb.append("SCRIPT_LOG:\n");
    }

    /**
     * Reports the creation of a {@code Task} via its core attributes
     *
     * @param id identification number
     * @param name name for activity
     * @param hours number of hours
     * @param dueDate number of days till due date
     */
    public void reportTaskCreation(int id, String name, int hours, int dueDate) {
        sb.append("TASK_CREATED: ID=").append(id);
        sb.append(", NAME=").append(name);
        sb.append(", HOURS=").append(hours);
        sb.append(", DUE=").append(dueDate).append("\n");
    }

    public void reportLabelCreation(int id, String name, int color) {
        sb.append("LABL_CREATED: ID=").append(id);
        sb.append(", NAME=").append(name);
        sb.append(", COLOR=").append(color).append("\n");
    }

    public void reportCheckListCreation(int id, String name) {
        sb.append("CHECKLIST_CREATED: ID=").append(id);
        sb.append(", NAME=").append(name).append("\n");
    }

    public void reportCardCreation(int id, String name) {
        sb.append("CARD_CREATED: ID=").append(id);
        sb.append(", NAME=").append(name).append("\n");
    }

    public void reportTaskAttr(int id, String attr, String[] args) {
        sb.append("TASK_ID: ID=").append(id);
        sb.append(", ATTR=").append(attr);
        sb.append(", ARGS=[");
        for(int i = 0; i < args.length; i++) {
            if(i > 0) {
                sb.append(", ");
            }
            sb.append(args[i]);
        }
        sb.append("]\n");
    }

    public void reportLabelAttr(int id, String attr, String[] args) {
        sb.append("LABEL_ID: ID=").append(id);
        sb.append(", ATTR=").append(attr);
        sb.append(", ARGS=[");
        for(int i = 0; i < args.length; i++) {
            if(i > 0) {
                sb.append(", ");
            }
            sb.append(args[i]);
        }
        sb.append("]\n");
    }

    public void reportCheckListEdit() {

    }

    public void reportCardEdit() {

    }

    public void reportAddFunc() {

    }

    public void reportRemoveFunc() {

    }

    public void reportLinkFile(String name) {
        sb.append("FILES_LINKED: FILE=").append(name).append("\n");
    }

    public void reportPrintFunc(Type[] args) {
        sb.append("PRINTS: ARGS=[");
        if(args.length > 1) {
//            sb.append(args[0].getVariableName() == null ? "NULL": args[0].getVariableName());
            sb.append("\"");
            sb.append(args[0].toString());
            sb.append("\"");
        }
        for(int i = 1; i < args.length; i++) {
            sb.append(", \"");
//            sb.append(args[i].getVariableName() == null ? "NULL": args[0].getVariableName());
            sb.append(args[i].toString());
            sb.append("\"");
        }
        sb.append("]\n");
    }

    public void reportPreProcessorSetup() {
        sb.append("PREPROC_ATTR: DEF_CONFIG=").append(ScriptFSM.isDefConfigPP());
        sb.append(", DEBUG=").append(ScriptFSM.isDebugPP());
        sb.append(", LOG=").append(ScriptFSM.isLogPP());
        sb.append(", BUILD=").append(ScriptFSM.isBuildPP());
        sb.append(", STATS=").append(ScriptFSM.isStatsPP()).append("\n");
    }

    public void reportFunctionSetup(CustomFunction customFunction) {
        sb.append("FUNC_SETUP: NAME=").append(customFunction.getFuncName());
        sb.append(", PARAM=[");
        if(customFunction.getArgs().length > 1) {
            sb.append(customFunction.getArgs()[0].trim());
        }
        for(int i = 1; i < customFunction.getArgs().length; i++) {
            sb.append(", ");
            sb.append(customFunction.getArgs()[i].trim());
        }
        sb.append("]\n");
    }

    public void reportFunctionCall(StaticFunction func) {
        sb.append("FUNC_CALLS: NAME=").append(func.getFuncName().trim());
        sb.append(", ARGS=[");
        if(func.getArgs().length > 1) {
            sb.append(func.getArgs()[0].trim());
        }
        for(int i = 1; i < func.getArgs().length; i++) {
            sb.append(", ");
            sb.append(func.getArgs()[i].trim());
        }
        sb.append("]\n");
    }

    public void reportAttrFunc(Attributes attr) {
        sb.append("ATTR_CALL: VAR_NAME=").append(attr.getVarName());
        sb.append(", NAME=").append(attr.getAttr());
        sb.append(", ARGS[");
        if(attr.getArgs().length > 1) {
            sb.append(attr.getArgs()[0].trim());
        }
        for(int i = 1; i < attr.getArgs().length; i++) {
            sb.append(", ");
            sb.append(attr.getArgs()[i].trim());
        }
        sb.append("]\n");
    }

    /**
     * Reports any exceptions thrown in the scripting language
     *
     * @param e {@code Exception} thrown
     */
    public void reportException(Throwable e) {
        sb.append("EXCEPTION=").append(e.getMessage()).append("\n");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
