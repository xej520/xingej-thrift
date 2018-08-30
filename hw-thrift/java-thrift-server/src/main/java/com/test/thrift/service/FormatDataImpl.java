package com.test.thrift.service;

import com.test.thrift.Data;
import com.test.thrift.format_data;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Service
public class FormatDataImpl implements format_data.Iface {


    @Override
    public Data doFormat(Data data) throws TException {
        data.text = "--->\t" + data.text.toUpperCase();

        return data;
    }
}
