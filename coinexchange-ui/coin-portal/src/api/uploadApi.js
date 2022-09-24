import request from "./request";

export const uploadApi = {
  aliyunUrl: process.env.BASE_API + "/v2/s/image/AliYunImgUpload",
  normalUrl: process.env.BASE_API + "/v2/s/image/commonImgUpload",
  aliyunFileUrl:
    "https://coin-exchange-file-imgs.oss-cn-hangzhou.aliyuncs.com/",
  getPreUpload() {
    const res = request({
      url: `/admin/image/pre/upload`,
      method: "get",
    });
    // console.log("getPreUpload", res);
    return res;
  },
};
