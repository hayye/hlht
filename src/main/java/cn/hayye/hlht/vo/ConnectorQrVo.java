package cn.hayye.hlht.vo;

public class ConnectorQrVo {

    private String protocol;
    private String connectorId;
    private String optCode;
    private String extend;

    public ConnectorQrVo() {
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
