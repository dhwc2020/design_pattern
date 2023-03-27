import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *  责任链
 *      优点: 1.
 *
 *      缺点: 1.
 */
public class ChainOfResponsibility {
    public static void main(String[] args) {
        AbstractManagerHandler managerHandler = new ProjectManagerHandler();
        AbstractManagerHandler ctoHandler = new CTOHandler();
        AbstractManagerHandler presidentHandler = new PresidentHandler();

        //设置处理器链路(项目经理 -> 技术总监 -> 总经理)
        managerHandler.successor(ctoHandler);
        ctoHandler.successor(presidentHandler);

        //封装请求
        Request request = new Request();
        request.setRequestType(RequestTypeEnum.COMPASSIONATE_LEAVE);
        request.setQuantity(1);
        request.setRequestContent("小李请一天事假去办银行卡");
        //请求审批
        managerHandler.handle(request);

        request.setQuantity(5);
        request.setRequestContent("小李请五天事假去办银行卡");
        managerHandler.handle(request);

        request.setQuantity(10);
        request.setRequestContent("小李请十天事假去办银行卡");
        managerHandler.handle(request);
    }
}

class Request{
    private RequestTypeEnum requestType;
    private String requestContent;
    //这个地方应该用时间段, 为方便演示以天为单位
    private int quantity;
    private ManagerLevelEnum requestLevel;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ManagerLevelEnum getRequestLevel() {
        return requestLevel;
    }

    public void setRequestLevel(ManagerLevelEnum requestLevel) {
        this.requestLevel = requestLevel;
    }

    public RequestTypeEnum getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }
}

//请求类型
enum RequestTypeEnum{
    COMPASSIONATE_LEAVE(1),
    SICK_LEAVE(2),
    MATERNITY_AND_PATERNITY_LEAVE(3),
    MARRIAGE_LEAVE(4),
    BEREAVEMENT_LEAVE(5);

    private int type;

    RequestTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

//管理级别
enum ManagerLevelEnum{
    PROJECT_MANAGER_LEVEL(1),
    CTO_LEVEL(2),
    PRESIDENT_LEVEL(3);

    private int level;

    ManagerLevelEnum(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}

abstract class AbstractManagerHandler{
    private final static Map<RequestTypeEnum, AbstractRequestLevelHandler> HANDLER_MAP = new HashMap<>();

    //初始化请求处理器
    static {
        HANDLER_MAP.put(RequestTypeEnum.COMPASSIONATE_LEAVE, new CompassionateLeaveRequestHandler());
        HANDLER_MAP.put(RequestTypeEnum.SICK_LEAVE, new SickLeaveRequestHandler());
        HANDLER_MAP.put(RequestTypeEnum.MATERNITY_AND_PATERNITY_LEAVE, new MaternityAndPaternityLeaveRequestHandler());
        HANDLER_MAP.put(RequestTypeEnum.MARRIAGE_LEAVE, new MarriageLeaveRequestHandler());
        HANDLER_MAP.put(RequestTypeEnum.BEREAVEMENT_LEAVE, new BereavementLeaveRequestHandler());
    }

    protected AbstractManagerHandler handler;

    //设置下一级处理器
    public void successor(AbstractManagerHandler handler){
        this.handler = handler;
    }

    public void handle(Request request){
        //根据请求类型分配处理器
        RequestTypeEnum requestType = request.getRequestType();
        AbstractRequestLevelHandler abstractRequestLevelHandler = HANDLER_MAP.get(requestType);
        //请求定级
        ManagerLevelEnum level = abstractRequestLevelHandler.requestScore(request);
        //设置请求
        request.setRequestLevel(level);

        //请求处理
        this.handleRequest(request);
    }

    //请求传递给下一个处理器
    protected void requestPass(Request request){
        Optional.ofNullable(this.handler).
                ifPresent((nextHandler -> nextHandler.handleRequest(request)));
    }

    protected abstract void handleRequest(Request request);
}

//请求定级
abstract class AbstractRequestLevelHandler{
    public abstract ManagerLevelEnum requestScore(Request request);
}

class CompassionateLeaveRequestHandler extends AbstractRequestLevelHandler{
    @Override
    public ManagerLevelEnum requestScore(Request request) {
        int quantity = request.getQuantity();

        if(quantity <= 1){
            return ManagerLevelEnum.PROJECT_MANAGER_LEVEL;
        }else if (quantity <= 7){
            return ManagerLevelEnum.CTO_LEVEL;
        }else {
            return ManagerLevelEnum.PRESIDENT_LEVEL;
        }
    }
}

class SickLeaveRequestHandler extends AbstractRequestLevelHandler{
    @Override
    public ManagerLevelEnum requestScore(Request request) {
        int quantity = request.getQuantity();

        if(quantity <= 1){
            return ManagerLevelEnum.PROJECT_MANAGER_LEVEL;
        }else if (quantity <= 3){
            return ManagerLevelEnum.CTO_LEVEL;
        }else {
            return ManagerLevelEnum.PRESIDENT_LEVEL;
        }
    }
}

class MaternityAndPaternityLeaveRequestHandler extends AbstractRequestLevelHandler{
    @Override
    public ManagerLevelEnum requestScore(Request request) {
        int quantity = request.getQuantity();

        if(quantity <= 60){
            return ManagerLevelEnum.PROJECT_MANAGER_LEVEL;
        }else if (quantity <= 90){
            return ManagerLevelEnum.CTO_LEVEL;
        }else {
            return ManagerLevelEnum.PRESIDENT_LEVEL;
        }
    }
}

class MarriageLeaveRequestHandler extends AbstractRequestLevelHandler{
    @Override
    public ManagerLevelEnum requestScore(Request request) {
        int quantity = request.getQuantity();

        if(quantity <= 3){
            return ManagerLevelEnum.PROJECT_MANAGER_LEVEL;
        }else if (quantity <= 7){
            return ManagerLevelEnum.CTO_LEVEL;
        }else {
            return ManagerLevelEnum.PRESIDENT_LEVEL;
        }
    }
}

class BereavementLeaveRequestHandler extends AbstractRequestLevelHandler{
    @Override
    public ManagerLevelEnum requestScore(Request request) {
        int quantity = request.getQuantity();

        if(quantity <= 3){
            return ManagerLevelEnum.PROJECT_MANAGER_LEVEL;
        }else if (quantity <= 5){
            return ManagerLevelEnum.CTO_LEVEL;
        }else {
            return ManagerLevelEnum.PRESIDENT_LEVEL;
        }
    }
}

class ProjectManagerHandler extends AbstractManagerHandler{
    @Override
    protected void handleRequest(Request request) {
        if (request.getRequestLevel().equals(ManagerLevelEnum.PROJECT_MANAGER_LEVEL)){
            System.out.println("项目经理审批通过: " + request.getRequestContent());
            return;
        }

        //处理不了, 传递给下一级处理器
        super.requestPass(request);
    }
}

class CTOHandler extends AbstractManagerHandler{
    @Override
    protected void handleRequest(Request request) {
        if (request.getRequestLevel().equals(ManagerLevelEnum.CTO_LEVEL)){
            System.out.println("技术总监审批通过: " + request.getRequestContent());
            return;
        }

        //处理不了, 传递给下一级处理器
        super.requestPass(request);
    }
}

class PresidentHandler extends AbstractManagerHandler{
    @Override
    protected void handleRequest(Request request) {
        if (request.getRequestLevel().equals(ManagerLevelEnum.PRESIDENT_LEVEL)){
            System.out.println("总经理审批通过: " + request.getRequestContent());
            return;
        }

        //处理不了, 传递给下一级处理器
        super.requestPass(request);
    }
}