namespace java com.test.thrift
namespace  go pkg.service

struct Data {
    1: string text;
}

service format_data {
     Data doFormat(1:Data data);
 }