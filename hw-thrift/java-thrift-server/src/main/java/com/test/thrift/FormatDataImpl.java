package com.test.thrift;

import org.apache.thrift.TException;

public class FormatDataImpl implements format_data.Iface {

    public Data doFormat(Data data) throws TException {
        data.text = "--->\t" + data.text.toUpperCase();

        return data;
    }
}
