/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : account

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 23/11/2021 00:11:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log_signin
-- ----------------------------
DROP TABLE IF EXISTS `log_signin`;
CREATE TABLE `log_signin` (
  `id` bigint(20) NOT NULL,
  `username` varchar(10) NOT NULL COMMENT '登录账号',
  `os` varchar(20) NOT NULL COMMENT '操作系统',
  `browser` varchar(20) NOT NULL COMMENT '浏览器',
  `ip` varchar(15) NOT NULL COMMENT 'ip地址',
  `create_time` bigint(13) NOT NULL COMMENT '登录时间',
  `status` tinyint(1) NOT NULL COMMENT '登录状态\n0-失败\n1-成功',
  `msg` varchar(100) NOT NULL COMMENT '信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';


-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `region_id` varchar(6) NOT NULL,
  `parent_id` varchar(6) NOT NULL COMMENT '上级地区id',
  `name` varchar(20) NOT NULL COMMENT '地区名称',
  `en_name` varchar(50) NOT NULL COMMENT '英文地区名称',
  PRIMARY KEY (`region_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of region
-- ----------------------------
BEGIN;
INSERT INTO `region` VALUES ('110000', '0', '北京', 'Beijing');
INSERT INTO `region` VALUES ('110100', '110000', '北京市', 'Beijing');
INSERT INTO `region` VALUES ('120000', '0', '天津', 'Tianjin');
INSERT INTO `region` VALUES ('120100', '120000', '天津市', 'Tianjin');
INSERT INTO `region` VALUES ('130000', '0', '河北省', 'Hebei Province');
INSERT INTO `region` VALUES ('130100', '130000', '石家庄市', 'Shijiazhuang');
INSERT INTO `region` VALUES ('130200', '130000', '唐山市', 'Tangshan');
INSERT INTO `region` VALUES ('130300', '130000', '秦皇岛市', 'Qinhuangdao');
INSERT INTO `region` VALUES ('130400', '130000', '邯郸市', 'Handan');
INSERT INTO `region` VALUES ('130500', '130000', '邢台市', 'Xingtai');
INSERT INTO `region` VALUES ('130600', '130000', '保定市', 'Baoding');
INSERT INTO `region` VALUES ('130700', '130000', '张家口市', 'Zhangjiakou');
INSERT INTO `region` VALUES ('130800', '130000', '承德市', 'Chengde');
INSERT INTO `region` VALUES ('130900', '130000', '沧州市', 'Cangzhou');
INSERT INTO `region` VALUES ('131000', '130000', '廊坊市', 'Langfang');
INSERT INTO `region` VALUES ('131100', '130000', '衡水市', 'Hengshui');
INSERT INTO `region` VALUES ('140000', '0', '山西省', 'Shanxi Province');
INSERT INTO `region` VALUES ('140100', '140000', '太原市', 'Taiyuan');
INSERT INTO `region` VALUES ('140200', '140000', '大同市', 'Datong');
INSERT INTO `region` VALUES ('140300', '140000', '阳泉市', 'Yangquan');
INSERT INTO `region` VALUES ('140400', '140000', '长治市', 'Changzhi');
INSERT INTO `region` VALUES ('140500', '140000', '晋城市', 'Jincheng');
INSERT INTO `region` VALUES ('140600', '140000', '朔州市', 'Shuozhou');
INSERT INTO `region` VALUES ('140700', '140000', '晋中市', 'Jinzhong');
INSERT INTO `region` VALUES ('140800', '140000', '运城市', 'Yuncheng');
INSERT INTO `region` VALUES ('140900', '140000', '忻州市', 'Xinzhou');
INSERT INTO `region` VALUES ('141000', '140000', '临汾市', 'Linfen');
INSERT INTO `region` VALUES ('141100', '140000', '吕梁市', 'Lvliang');
INSERT INTO `region` VALUES ('150000', '0', '内蒙古自治区', 'Inner Mongolia');
INSERT INTO `region` VALUES ('150100', '150000', '呼和浩特市', 'Hohhot');
INSERT INTO `region` VALUES ('150200', '150000', '包头市', 'Baotou');
INSERT INTO `region` VALUES ('150300', '150000', '乌海市', 'Wuhai');
INSERT INTO `region` VALUES ('150400', '150000', '赤峰市', 'Chifeng');
INSERT INTO `region` VALUES ('150500', '150000', '通辽市', 'Tongliao');
INSERT INTO `region` VALUES ('150600', '150000', '鄂尔多斯市', 'Ordos');
INSERT INTO `region` VALUES ('150700', '150000', '呼伦贝尔市', 'Hulunbuir');
INSERT INTO `region` VALUES ('150800', '150000', '巴彦淖尔市', 'Bayannur');
INSERT INTO `region` VALUES ('150900', '150000', '乌兰察布市', 'Ulanqab');
INSERT INTO `region` VALUES ('152200', '150000', '兴安盟', 'Hinggan League');
INSERT INTO `region` VALUES ('152500', '150000', '锡林郭勒盟', 'Xilingol League');
INSERT INTO `region` VALUES ('152900', '150000', '阿拉善盟', 'Alashan League');
INSERT INTO `region` VALUES ('210000', '0', '辽宁省', 'Liaoning Province');
INSERT INTO `region` VALUES ('210100', '210000', '沈阳市', 'Shenyang');
INSERT INTO `region` VALUES ('210200', '210000', '大连市', 'Dalian');
INSERT INTO `region` VALUES ('210300', '210000', '鞍山市', 'Anshan');
INSERT INTO `region` VALUES ('210400', '210000', '抚顺市', 'Fushun');
INSERT INTO `region` VALUES ('210500', '210000', '本溪市', 'Benxi');
INSERT INTO `region` VALUES ('210600', '210000', '丹东市', 'Dandong');
INSERT INTO `region` VALUES ('210700', '210000', '锦州市', 'Jinzhou');
INSERT INTO `region` VALUES ('210800', '210000', '营口市', 'Yingkou');
INSERT INTO `region` VALUES ('210900', '210000', '阜新市', 'Fuxin');
INSERT INTO `region` VALUES ('211000', '210000', '辽阳市', 'Liaoyang');
INSERT INTO `region` VALUES ('211100', '210000', '盘锦市', 'Panjin');
INSERT INTO `region` VALUES ('211200', '210000', '铁岭市', 'Tieling');
INSERT INTO `region` VALUES ('211300', '210000', '朝阳市', 'Chaoyang');
INSERT INTO `region` VALUES ('211400', '210000', '葫芦岛市', 'Huludao');
INSERT INTO `region` VALUES ('220000', '0', '吉林省', 'Jilin Province');
INSERT INTO `region` VALUES ('220100', '220000', '长春市', 'Changchun');
INSERT INTO `region` VALUES ('220200', '220000', '吉林市', 'Jilin');
INSERT INTO `region` VALUES ('220300', '220000', '四平市', 'Siping');
INSERT INTO `region` VALUES ('220400', '220000', '辽源市', 'Liaoyuan');
INSERT INTO `region` VALUES ('220500', '220000', '通化市', 'Tonghua');
INSERT INTO `region` VALUES ('220600', '220000', '白山市', 'Baishan');
INSERT INTO `region` VALUES ('220700', '220000', '松原市', 'Songyuan');
INSERT INTO `region` VALUES ('220800', '220000', '白城市', 'Baicheng');
INSERT INTO `region` VALUES ('222400', '220000', '延边朝鲜族自治州', 'Yanbian Korean Autonomous Prefecture');
INSERT INTO `region` VALUES ('230000', '0', '黑龙江省', 'Heilongjiang Province');
INSERT INTO `region` VALUES ('230100', '230000', '哈尔滨市', 'Harbin');
INSERT INTO `region` VALUES ('230200', '230000', '齐齐哈尔市', 'Qiqihar');
INSERT INTO `region` VALUES ('230300', '230000', '鸡西市', 'Jixi');
INSERT INTO `region` VALUES ('230400', '230000', '鹤岗市', 'Hegang');
INSERT INTO `region` VALUES ('230500', '230000', '双鸭山市', 'Shuangyashan');
INSERT INTO `region` VALUES ('230600', '230000', '大庆市', 'Daqing');
INSERT INTO `region` VALUES ('230700', '230000', '伊春市', 'Yichun');
INSERT INTO `region` VALUES ('230800', '230000', '佳木斯市', 'Jiamusi');
INSERT INTO `region` VALUES ('230900', '230000', '七台河市', 'Qitaihe');
INSERT INTO `region` VALUES ('231000', '230000', '牡丹江市', 'Mudanjiang');
INSERT INTO `region` VALUES ('231100', '230000', '黑河市', 'Heihe');
INSERT INTO `region` VALUES ('231200', '230000', '绥化市', 'Suihua');
INSERT INTO `region` VALUES ('232700', '230000', '大兴安岭地区', 'Daxing\'anling Prefecture');
INSERT INTO `region` VALUES ('310000', '0', '上海', 'Shanghai');
INSERT INTO `region` VALUES ('310100', '310000', '上海市', 'Shanghai');
INSERT INTO `region` VALUES ('320000', '0', '江苏省', 'Jiangsu Province');
INSERT INTO `region` VALUES ('320100', '320000', '南京市', 'Nanjing');
INSERT INTO `region` VALUES ('320200', '320000', '无锡市', 'Wuxi');
INSERT INTO `region` VALUES ('320300', '320000', '徐州市', 'Xuzhou');
INSERT INTO `region` VALUES ('320400', '320000', '常州市', 'Changzhou');
INSERT INTO `region` VALUES ('320500', '320000', '苏州市', 'Suzhou');
INSERT INTO `region` VALUES ('320600', '320000', '南通市', 'Nantong');
INSERT INTO `region` VALUES ('320700', '320000', '连云港市', 'Lianyungang');
INSERT INTO `region` VALUES ('320800', '320000', '淮安市', 'Huai\'an');
INSERT INTO `region` VALUES ('320900', '320000', '盐城市', 'Yancheng');
INSERT INTO `region` VALUES ('321000', '320000', '扬州市', 'Yangzhou');
INSERT INTO `region` VALUES ('321100', '320000', '镇江市', 'Zhenjiang');
INSERT INTO `region` VALUES ('321200', '320000', '泰州市', 'Taizhou');
INSERT INTO `region` VALUES ('321300', '320000', '宿迁市', 'Suqian');
INSERT INTO `region` VALUES ('330000', '0', '浙江省', 'Zhejiang Province');
INSERT INTO `region` VALUES ('330100', '330000', '杭州市', 'Hangzhou');
INSERT INTO `region` VALUES ('330200', '330000', '宁波市', 'Ningbo');
INSERT INTO `region` VALUES ('330300', '330000', '温州市', 'Wenzhou');
INSERT INTO `region` VALUES ('330400', '330000', '嘉兴市', 'Jiaxing');
INSERT INTO `region` VALUES ('330500', '330000', '湖州市', 'Huzhou');
INSERT INTO `region` VALUES ('330600', '330000', '绍兴市', 'Shaoxing');
INSERT INTO `region` VALUES ('330700', '330000', '金华市', 'Jinhua');
INSERT INTO `region` VALUES ('330800', '330000', '衢州市', 'Quzhou');
INSERT INTO `region` VALUES ('330900', '330000', '舟山市', 'Zhoushan');
INSERT INTO `region` VALUES ('331000', '330000', '台州市', 'Taizhou');
INSERT INTO `region` VALUES ('331100', '330000', '丽水市', 'Lishui');
INSERT INTO `region` VALUES ('340000', '0', '安徽省', 'Anhui Province');
INSERT INTO `region` VALUES ('340100', '340000', '合肥市', 'Hefei');
INSERT INTO `region` VALUES ('340200', '340000', '芜湖市', 'Wuhu');
INSERT INTO `region` VALUES ('340300', '340000', '蚌埠市', 'Bangbu');
INSERT INTO `region` VALUES ('340400', '340000', '淮南市', 'Huainan');
INSERT INTO `region` VALUES ('340500', '340000', '马鞍山市', 'Ma\'anshan');
INSERT INTO `region` VALUES ('340600', '340000', '淮北市', 'Huaibei');
INSERT INTO `region` VALUES ('340700', '340000', '铜陵市', 'Tongling');
INSERT INTO `region` VALUES ('340800', '340000', '安庆市', 'Anqing');
INSERT INTO `region` VALUES ('341000', '340000', '黄山市', 'Huangshan');
INSERT INTO `region` VALUES ('341100', '340000', '滁州市', 'Chuzhou');
INSERT INTO `region` VALUES ('341200', '340000', '阜阳市', 'Fuyang');
INSERT INTO `region` VALUES ('341300', '340000', '宿州市', 'Suzhou');
INSERT INTO `region` VALUES ('341500', '340000', '六安市', 'Lu\'an');
INSERT INTO `region` VALUES ('341600', '340000', '亳州市', 'Bozhou');
INSERT INTO `region` VALUES ('341700', '340000', '池州市', 'Chizhou');
INSERT INTO `region` VALUES ('341800', '340000', '宣城市', 'Xuancheng');
INSERT INTO `region` VALUES ('350000', '0', '福建省', 'Fujian Province');
INSERT INTO `region` VALUES ('350100', '350000', '福州市', 'Fuzhou');
INSERT INTO `region` VALUES ('350200', '350000', '厦门市', 'Xiamen');
INSERT INTO `region` VALUES ('350300', '350000', '莆田市', 'Putian');
INSERT INTO `region` VALUES ('350400', '350000', '三明市', 'Sanming');
INSERT INTO `region` VALUES ('350500', '350000', '泉州市', 'Quanzhou');
INSERT INTO `region` VALUES ('350600', '350000', '漳州市', 'Zhangzhou');
INSERT INTO `region` VALUES ('350700', '350000', '南平市', 'Nanping');
INSERT INTO `region` VALUES ('350800', '350000', '龙岩市', 'Longyan');
INSERT INTO `region` VALUES ('350900', '350000', '宁德市', 'Ningde');
INSERT INTO `region` VALUES ('360000', '0', '江西省', 'Jiangxi Province');
INSERT INTO `region` VALUES ('360100', '360000', '南昌市', 'Nanchang');
INSERT INTO `region` VALUES ('360200', '360000', '景德镇市', 'Jingdezhen');
INSERT INTO `region` VALUES ('360300', '360000', '萍乡市', 'Pingxiang');
INSERT INTO `region` VALUES ('360400', '360000', '九江市', 'Jiujiang');
INSERT INTO `region` VALUES ('360500', '360000', '新余市', 'Xinyu');
INSERT INTO `region` VALUES ('360600', '360000', '鹰潭市', 'Yingtan');
INSERT INTO `region` VALUES ('360700', '360000', '赣州市', 'Ganzhou');
INSERT INTO `region` VALUES ('360800', '360000', '吉安市', 'Ji\'an');
INSERT INTO `region` VALUES ('360900', '360000', '宜春市', 'Yichun');
INSERT INTO `region` VALUES ('361000', '360000', '抚州市', 'Fuzhou');
INSERT INTO `region` VALUES ('361100', '360000', '上饶市', 'Shangrao');
INSERT INTO `region` VALUES ('370000', '0', '山东省', 'Shandong Province');
INSERT INTO `region` VALUES ('370100', '370000', '济南市', 'Jinan');
INSERT INTO `region` VALUES ('370200', '370000', '青岛市', 'Qingdao');
INSERT INTO `region` VALUES ('370300', '370000', '淄博市', 'Zibo');
INSERT INTO `region` VALUES ('370400', '370000', '枣庄市', 'Zaozhuang');
INSERT INTO `region` VALUES ('370500', '370000', '东营市', 'Dongying');
INSERT INTO `region` VALUES ('370600', '370000', '烟台市', 'Yantai');
INSERT INTO `region` VALUES ('370700', '370000', '潍坊市', 'Weifang');
INSERT INTO `region` VALUES ('370800', '370000', '济宁市', 'Jining');
INSERT INTO `region` VALUES ('370900', '370000', '泰安市', 'Tai\'an');
INSERT INTO `region` VALUES ('371000', '370000', '威海市', 'Weihai');
INSERT INTO `region` VALUES ('371100', '370000', '日照市', 'Rizhao');
INSERT INTO `region` VALUES ('371200', '370000', '莱芜市', 'Laiwu');
INSERT INTO `region` VALUES ('371300', '370000', '临沂市', 'Linyi');
INSERT INTO `region` VALUES ('371400', '370000', '德州市', 'Dezhou');
INSERT INTO `region` VALUES ('371500', '370000', '聊城市', 'Liaocheng');
INSERT INTO `region` VALUES ('371600', '370000', '滨州市', 'Binzhou');
INSERT INTO `region` VALUES ('371700', '370000', '菏泽市', 'Heze');
INSERT INTO `region` VALUES ('410000', '0', '河南省', 'Henan Province');
INSERT INTO `region` VALUES ('410100', '410000', '郑州市', 'Zhengzhou');
INSERT INTO `region` VALUES ('410200', '410000', '开封市', 'Kaifeng');
INSERT INTO `region` VALUES ('410300', '410000', '洛阳市', 'Luoyang');
INSERT INTO `region` VALUES ('410400', '410000', '平顶山市', 'Pingdingshan');
INSERT INTO `region` VALUES ('410500', '410000', '安阳市', 'Anyang');
INSERT INTO `region` VALUES ('410600', '410000', '鹤壁市', 'Hebi');
INSERT INTO `region` VALUES ('410700', '410000', '新乡市', 'Xinxiang');
INSERT INTO `region` VALUES ('410800', '410000', '焦作市', 'Jiaozuo');
INSERT INTO `region` VALUES ('410881', '410000', '济源市', 'Jiyuan');
INSERT INTO `region` VALUES ('410900', '410000', '濮阳市', 'Puyang');
INSERT INTO `region` VALUES ('411000', '410000', '许昌市', 'Xuchang');
INSERT INTO `region` VALUES ('411100', '410000', '漯河市', 'Leihe');
INSERT INTO `region` VALUES ('411200', '410000', '三门峡市', 'Sanmenxia');
INSERT INTO `region` VALUES ('411300', '410000', '南阳市', 'Nanyang');
INSERT INTO `region` VALUES ('411400', '410000', '商丘市', 'Shangqiu');
INSERT INTO `region` VALUES ('411500', '410000', '信阳市', 'Xinyang');
INSERT INTO `region` VALUES ('411600', '410000', '周口市', 'Zhoukou');
INSERT INTO `region` VALUES ('411700', '410000', '驻马店市', 'Zhumadian');
INSERT INTO `region` VALUES ('420000', '0', '湖北省', 'Hubei Province');
INSERT INTO `region` VALUES ('420100', '420000', '武汉市', 'Wuhan');
INSERT INTO `region` VALUES ('420200', '420000', '黄石市', 'Huangshi');
INSERT INTO `region` VALUES ('420300', '420000', '十堰市', 'Shiyan');
INSERT INTO `region` VALUES ('420500', '420000', '宜昌市', 'Yichang');
INSERT INTO `region` VALUES ('420600', '420000', '襄阳市', 'Xiangyang');
INSERT INTO `region` VALUES ('420700', '420000', '鄂州市', 'Ezhou');
INSERT INTO `region` VALUES ('420800', '420000', '荆门市', 'Jingmen');
INSERT INTO `region` VALUES ('420900', '420000', '孝感市', 'Xiaogan');
INSERT INTO `region` VALUES ('421000', '420000', '荆州市', 'Jingzhou');
INSERT INTO `region` VALUES ('421100', '420000', '黄冈市', 'Huanggang');
INSERT INTO `region` VALUES ('421200', '420000', '咸宁市', 'Xianning');
INSERT INTO `region` VALUES ('421300', '420000', '随州市', 'Suizhou');
INSERT INTO `region` VALUES ('422800', '420000', '恩施土家族苗族自治州', 'Enshi Tujia and Miao Autonomous Prefecture');
INSERT INTO `region` VALUES ('429004', '420000', '仙桃市', 'Xiantao');
INSERT INTO `region` VALUES ('429005', '420000', '潜江市', 'Qianjiang');
INSERT INTO `region` VALUES ('429006', '420000', '天门市', 'Tianmen');
INSERT INTO `region` VALUES ('429021', '420000', '神农架林区', 'Shennongjia');
INSERT INTO `region` VALUES ('430000', '0', '湖南省', 'Hunan Province');
INSERT INTO `region` VALUES ('430100', '430000', '长沙市', 'Changsha');
INSERT INTO `region` VALUES ('430200', '430000', '株洲市', 'Zhuzhou');
INSERT INTO `region` VALUES ('430300', '430000', '湘潭市', 'Xiangtan');
INSERT INTO `region` VALUES ('430400', '430000', '衡阳市', 'Hengyang');
INSERT INTO `region` VALUES ('430500', '430000', '邵阳市', 'Shaoyang');
INSERT INTO `region` VALUES ('430600', '430000', '岳阳市', 'Yueyang');
INSERT INTO `region` VALUES ('430700', '430000', '常德市', 'Changde');
INSERT INTO `region` VALUES ('430800', '430000', '张家界市', 'Zhangjiajie');
INSERT INTO `region` VALUES ('430900', '430000', '益阳市', 'Yiyang');
INSERT INTO `region` VALUES ('431000', '430000', '郴州市', 'Chenzhou');
INSERT INTO `region` VALUES ('431100', '430000', '永州市', 'Yongzhou');
INSERT INTO `region` VALUES ('431200', '430000', '怀化市', 'Huaihua');
INSERT INTO `region` VALUES ('431300', '430000', '娄底市', 'Loudi');
INSERT INTO `region` VALUES ('433100', '430000', '湘西土家族苗族自治州', 'Xiangxi Tujia and Miao Autonomous Prefecture');
INSERT INTO `region` VALUES ('440000', '0', '广东省', 'Guangdong Province');
INSERT INTO `region` VALUES ('440100', '440000', '广州市', 'Guangzhou');
INSERT INTO `region` VALUES ('440200', '440000', '韶关市', 'Shaoguan');
INSERT INTO `region` VALUES ('440300', '440000', '深圳市', 'Shenzhen');
INSERT INTO `region` VALUES ('440400', '440000', '珠海市', 'Zhuhai');
INSERT INTO `region` VALUES ('440500', '440000', '汕头市', 'Shantou');
INSERT INTO `region` VALUES ('440600', '440000', '佛山市', 'Foshan');
INSERT INTO `region` VALUES ('440700', '440000', '江门市', 'Jiangmen');
INSERT INTO `region` VALUES ('440800', '440000', '湛江市', 'Zhanjiang');
INSERT INTO `region` VALUES ('440900', '440000', '茂名市', 'Maoming');
INSERT INTO `region` VALUES ('441200', '440000', '肇庆市', 'Zhaoqing');
INSERT INTO `region` VALUES ('441300', '440000', '惠州市', 'Huizhou');
INSERT INTO `region` VALUES ('441400', '440000', '梅州市', 'Meizhou');
INSERT INTO `region` VALUES ('441500', '440000', '汕尾市', 'Shanwei');
INSERT INTO `region` VALUES ('441600', '440000', '河源市', 'Heyuan');
INSERT INTO `region` VALUES ('441700', '440000', '阳江市', 'Yangjiang');
INSERT INTO `region` VALUES ('441800', '440000', '清远市', 'Qingyuan');
INSERT INTO `region` VALUES ('441900', '440000', '东莞市', 'Dongguan');
INSERT INTO `region` VALUES ('442000', '440000', '中山市', 'Zhongshan');
INSERT INTO `region` VALUES ('442101', '440000', '东沙群岛', 'Dongsha Islands');
INSERT INTO `region` VALUES ('445100', '440000', '潮州市', 'Chaozhou');
INSERT INTO `region` VALUES ('445200', '440000', '揭阳市', 'Jieyang');
INSERT INTO `region` VALUES ('445300', '440000', '云浮市', 'Yunfu');
INSERT INTO `region` VALUES ('450000', '0', '广西壮族自治区', 'Guangxi Zhuang Autonomous Region');
INSERT INTO `region` VALUES ('450100', '450000', '南宁市', 'Nanning');
INSERT INTO `region` VALUES ('450200', '450000', '柳州市', 'Liuzhou');
INSERT INTO `region` VALUES ('450300', '450000', '桂林市', 'Guilin');
INSERT INTO `region` VALUES ('450400', '450000', '梧州市', 'Wuzhou');
INSERT INTO `region` VALUES ('450500', '450000', '北海市', 'Beihai');
INSERT INTO `region` VALUES ('450600', '450000', '防城港市', 'Fangchenggang');
INSERT INTO `region` VALUES ('450700', '450000', '钦州市', 'Qinzhou');
INSERT INTO `region` VALUES ('450800', '450000', '贵港市', 'Guigang');
INSERT INTO `region` VALUES ('450900', '450000', '玉林市', 'Yulin');
INSERT INTO `region` VALUES ('451000', '450000', '百色市', 'Baise');
INSERT INTO `region` VALUES ('451100', '450000', '贺州市', 'Hezhou');
INSERT INTO `region` VALUES ('451200', '450000', '河池市', 'Hechi');
INSERT INTO `region` VALUES ('451300', '450000', '来宾市', 'Laibin');
INSERT INTO `region` VALUES ('451400', '450000', '崇左市', 'Chongzuo');
INSERT INTO `region` VALUES ('460000', '0', '海南省', 'Hainan Province');
INSERT INTO `region` VALUES ('460100', '460000', '海口市', 'Haikou');
INSERT INTO `region` VALUES ('460200', '460000', '三亚市', 'Sanya');
INSERT INTO `region` VALUES ('460300', '460000', '三沙市', 'Sansha');
INSERT INTO `region` VALUES ('469001', '460000', '五指山市', 'Wuzhishan');
INSERT INTO `region` VALUES ('469002', '460000', '琼海市', 'Qionghai');
INSERT INTO `region` VALUES ('469003', '460000', '儋州市', 'Danzhou');
INSERT INTO `region` VALUES ('469005', '460000', '文昌市', 'Wenchang');
INSERT INTO `region` VALUES ('469006', '460000', '万宁市', 'Wanning');
INSERT INTO `region` VALUES ('469007', '460000', '东方市', 'Dongfang');
INSERT INTO `region` VALUES ('469025', '460000', '定安县', 'Ding\'an County');
INSERT INTO `region` VALUES ('469026', '460000', '屯昌县', 'Tunchang County');
INSERT INTO `region` VALUES ('469027', '460000', '澄迈县', 'Chengmai County');
INSERT INTO `region` VALUES ('469028', '460000', '临高县', 'Lingao County');
INSERT INTO `region` VALUES ('469030', '460000', '白沙黎族自治县', 'Baisha Li Autonomous County');
INSERT INTO `region` VALUES ('469031', '460000', '昌江黎族自治县', 'Changjiang Li Autonomous County');
INSERT INTO `region` VALUES ('469033', '460000', '乐东黎族自治县', 'Ledong Li Autonomous County');
INSERT INTO `region` VALUES ('469034', '460000', '陵水黎族自治县', 'Lingshui Li Autonomous County');
INSERT INTO `region` VALUES ('469035', '460000', '保亭黎族苗族自治县', 'Baoting Li and Miao Autonomous County');
INSERT INTO `region` VALUES ('469036', '460000', '琼中黎族苗族自治县', 'Qiongzhong Li and Miao Autonomous County');
INSERT INTO `region` VALUES ('469037', '460000', '西沙群岛', 'Xisha Islands');
INSERT INTO `region` VALUES ('469038', '460000', '南沙群岛', 'Nansha Islands');
INSERT INTO `region` VALUES ('469039', '460000', '中沙群岛的岛礁及其海域', 'Zhongsha Islands');
INSERT INTO `region` VALUES ('500000', '0', '重庆', 'Chongqing');
INSERT INTO `region` VALUES ('500100', '500000', '重庆市', 'Zhongqing');
INSERT INTO `region` VALUES ('510000', '0', '四川省', 'Sichuan Province');
INSERT INTO `region` VALUES ('510100', '510000', '成都市', 'Chengdu');
INSERT INTO `region` VALUES ('510300', '510000', '自贡市', 'Zigong');
INSERT INTO `region` VALUES ('510400', '510000', '攀枝花市', 'Panzhihua');
INSERT INTO `region` VALUES ('510500', '510000', '泸州市', 'Luzhou');
INSERT INTO `region` VALUES ('510600', '510000', '德阳市', 'Deyang');
INSERT INTO `region` VALUES ('510700', '510000', '绵阳市', 'Mianyang');
INSERT INTO `region` VALUES ('510800', '510000', '广元市', 'Guangyuan');
INSERT INTO `region` VALUES ('510900', '510000', '遂宁市', 'Suining');
INSERT INTO `region` VALUES ('511000', '510000', '内江市', 'Neijiang');
INSERT INTO `region` VALUES ('511100', '510000', '乐山市', 'Leshan');
INSERT INTO `region` VALUES ('511300', '510000', '南充市', 'Nanchong');
INSERT INTO `region` VALUES ('511400', '510000', '眉山市', 'Meishan');
INSERT INTO `region` VALUES ('511500', '510000', '宜宾市', 'Yibin');
INSERT INTO `region` VALUES ('511600', '510000', '广安市', 'Guang\'an');
INSERT INTO `region` VALUES ('511700', '510000', '达州市', 'Dazhou');
INSERT INTO `region` VALUES ('511800', '510000', '雅安市', 'Ya\'an');
INSERT INTO `region` VALUES ('511900', '510000', '巴中市', 'Bazhong');
INSERT INTO `region` VALUES ('512000', '510000', '资阳市', 'Ziyang');
INSERT INTO `region` VALUES ('513200', '510000', '阿坝藏族羌族自治州', 'Aba Tibetan and Qiang Autonomous Prefecture');
INSERT INTO `region` VALUES ('513300', '510000', '甘孜藏族自治州', 'Garze Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('513400', '510000', '凉山彝族自治州', 'Liangshan Yi Autonomous Prefecture');
INSERT INTO `region` VALUES ('520000', '0', '贵州省', 'Guizhou Province');
INSERT INTO `region` VALUES ('520100', '520000', '贵阳市', 'Guiyang');
INSERT INTO `region` VALUES ('520200', '520000', '六盘水市', 'Liupanshui');
INSERT INTO `region` VALUES ('520300', '520000', '遵义市', 'Zunyi');
INSERT INTO `region` VALUES ('520400', '520000', '安顺市', 'Anshun');
INSERT INTO `region` VALUES ('522200', '520000', '铜仁市', 'Tongren');
INSERT INTO `region` VALUES ('522300', '520000', '黔西南布依族苗族自治州', 'Qianxinan Bouyei and Miao Autonomous Prefecture');
INSERT INTO `region` VALUES ('522400', '520000', '毕节市', 'Bijie');
INSERT INTO `region` VALUES ('522600', '520000', '黔东南苗族侗族自治州', 'Qiandongnan Miao and Dong Autonomous Prefecture');
INSERT INTO `region` VALUES ('522700', '520000', '黔南布依族苗族自治州', 'Qiannan Bouyei and Miao Autonomous Prefecture');
INSERT INTO `region` VALUES ('530000', '0', '云南省', 'Yunnan Province');
INSERT INTO `region` VALUES ('530100', '530000', '昆明市', 'Kunming');
INSERT INTO `region` VALUES ('530300', '530000', '曲靖市', 'Qujing');
INSERT INTO `region` VALUES ('530400', '530000', '玉溪市', 'Yuxi');
INSERT INTO `region` VALUES ('530500', '530000', '保山市', 'Baoshan');
INSERT INTO `region` VALUES ('530600', '530000', '昭通市', 'Zhaotong');
INSERT INTO `region` VALUES ('530700', '530000', '丽江市', 'Lijiang');
INSERT INTO `region` VALUES ('530800', '530000', '普洱市', 'Pu\'er');
INSERT INTO `region` VALUES ('530900', '530000', '临沧市', 'Lincang');
INSERT INTO `region` VALUES ('532300', '530000', '楚雄彝族自治州', 'Chuxiong Yi Autonomous Prefecture');
INSERT INTO `region` VALUES ('532500', '530000', '红河哈尼族彝族自治州', 'Honghe Hani and Yi Autonomous Prefecture');
INSERT INTO `region` VALUES ('532600', '530000', '文山壮族苗族自治州', 'Wenshan Zhuang and Miao Autonomous Prefecture');
INSERT INTO `region` VALUES ('532800', '530000', '西双版纳傣族自治州', 'Xishuangbanna Dai Autonomous Prefecture');
INSERT INTO `region` VALUES ('532900', '530000', '大理白族自治州', 'Dali Bai Autonomous Prefecture');
INSERT INTO `region` VALUES ('533100', '530000', '德宏傣族景颇族自治州', 'Dehong Dai and Jingpo Autonomous Prefecture');
INSERT INTO `region` VALUES ('533300', '530000', '怒江傈僳族自治州', 'Nujiang Lisu Autonomous Prefecture');
INSERT INTO `region` VALUES ('533400', '530000', '迪庆藏族自治州', 'Diqing Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('540000', '0', '西藏自治区', 'Tibet Autonomous Region');
INSERT INTO `region` VALUES ('540100', '540000', '拉萨市', 'Lasa');
INSERT INTO `region` VALUES ('542100', '540000', '昌都市', 'Changdu');
INSERT INTO `region` VALUES ('542200', '540000', '山南市', 'Shannan');
INSERT INTO `region` VALUES ('542300', '540000', '日喀则市', 'Shigatse');
INSERT INTO `region` VALUES ('542400', '540000', '那曲市', 'Naqu');
INSERT INTO `region` VALUES ('542500', '540000', '阿里地区', 'Ngari Prefecture');
INSERT INTO `region` VALUES ('542600', '540000', '林芝市', 'Linzhi');
INSERT INTO `region` VALUES ('610000', '0', '陕西省', 'Shaanxi Province');
INSERT INTO `region` VALUES ('610100', '610000', '西安市', 'Xi\'an');
INSERT INTO `region` VALUES ('610200', '610000', '铜川市', 'Tongchuan');
INSERT INTO `region` VALUES ('610300', '610000', '宝鸡市', 'Baoji');
INSERT INTO `region` VALUES ('610400', '610000', '咸阳市', 'Xianyang');
INSERT INTO `region` VALUES ('610500', '610000', '渭南市', 'Weinan');
INSERT INTO `region` VALUES ('610600', '610000', '延安市', 'Yan\'an');
INSERT INTO `region` VALUES ('610700', '610000', '汉中市', 'Hanzhong');
INSERT INTO `region` VALUES ('610800', '610000', '榆林市', 'Yulin');
INSERT INTO `region` VALUES ('610900', '610000', '安康市', 'Ankang');
INSERT INTO `region` VALUES ('611000', '610000', '商洛市', 'Shangluo');
INSERT INTO `region` VALUES ('620000', '0', '甘肃省', 'Gansu Province');
INSERT INTO `region` VALUES ('620100', '620000', '兰州市', 'Lanzhou');
INSERT INTO `region` VALUES ('620200', '620000', '嘉峪关市', 'Jiayuguan');
INSERT INTO `region` VALUES ('620300', '620000', '金昌市', 'Jinchang');
INSERT INTO `region` VALUES ('620400', '620000', '白银市', 'Baiyin');
INSERT INTO `region` VALUES ('620500', '620000', '天水市', 'Tianshui');
INSERT INTO `region` VALUES ('620600', '620000', '武威市', 'Wuwei');
INSERT INTO `region` VALUES ('620700', '620000', '张掖市', 'Zhangye');
INSERT INTO `region` VALUES ('620800', '620000', '平凉市', 'Pingliang');
INSERT INTO `region` VALUES ('620900', '620000', '酒泉市', 'Jiuquan');
INSERT INTO `region` VALUES ('621000', '620000', '庆阳市', 'Qingyang');
INSERT INTO `region` VALUES ('621100', '620000', '定西市', 'Dingxi');
INSERT INTO `region` VALUES ('621200', '620000', '陇南市', 'Longnan');
INSERT INTO `region` VALUES ('622900', '620000', '临夏回族自治州', 'Linxia Hui Autonomous Prefecture');
INSERT INTO `region` VALUES ('623000', '620000', '甘南藏族自治州', 'Gannan Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('630000', '0', '青海省', 'Qinghai Province');
INSERT INTO `region` VALUES ('630100', '630000', '西宁市', 'Xining');
INSERT INTO `region` VALUES ('632100', '630000', '海东市', 'Haidong');
INSERT INTO `region` VALUES ('632200', '630000', '海北藏族自治州', 'Haibei Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('632300', '630000', '黄南藏族自治州', 'Huangnan Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('632500', '630000', '海南藏族自治州', 'Hainan Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('632600', '630000', '果洛藏族自治州', 'Golog Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('632700', '630000', '玉树藏族自治州', 'Yushu Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('632800', '630000', '海西蒙古族藏族自治州', 'Haixi Mongol and Tibetan Autonomous Prefecture');
INSERT INTO `region` VALUES ('640000', '0', '宁夏回族自治区', 'Ningxia Hui Autonomous Region');
INSERT INTO `region` VALUES ('640100', '640000', '银川市', 'Yinchuan');
INSERT INTO `region` VALUES ('640200', '640000', '石嘴山市', 'Shizuishan');
INSERT INTO `region` VALUES ('640300', '640000', '吴忠市', 'Wuzhong');
INSERT INTO `region` VALUES ('640400', '640000', '固原市', 'Guyuan');
INSERT INTO `region` VALUES ('640500', '640000', '中卫市', 'Zhongwei');
INSERT INTO `region` VALUES ('650000', '0', '新疆维吾尔自治区', 'Xinjiang Uygur Autonomous Region');
INSERT INTO `region` VALUES ('650100', '650000', '乌鲁木齐市', 'Urumqi ');
INSERT INTO `region` VALUES ('650200', '650000', '克拉玛依市', 'Karamay');
INSERT INTO `region` VALUES ('652100', '650000', '吐鲁番市', 'Turpan');
INSERT INTO `region` VALUES ('652200', '650000', '哈密市', 'Hami');
INSERT INTO `region` VALUES ('652300', '650000', '昌吉回族自治州', 'Changji Hui Autonomous Prefecture');
INSERT INTO `region` VALUES ('652700', '650000', '博尔塔拉蒙古自治州', 'Boertala Mongolian Autonomous Prefecture');
INSERT INTO `region` VALUES ('652800', '650000', '巴音郭楞蒙古自治州', 'Bayingolin Mongol Autonomous Prefecture');
INSERT INTO `region` VALUES ('652900', '650000', '阿克苏地区', 'Akesu Prefecture');
INSERT INTO `region` VALUES ('653000', '650000', '克孜勒苏柯尔克孜自治州', 'Kezilesukeerkezi Autonomous Prefecture');
INSERT INTO `region` VALUES ('653100', '650000', '喀什地区', 'Kashgar Prefecture');
INSERT INTO `region` VALUES ('653200', '650000', '和田地区', 'Hotan Prefecture');
INSERT INTO `region` VALUES ('654000', '650000', '伊犁哈萨克自治州', 'Yili Kazakh Autonomous Prefecture');
INSERT INTO `region` VALUES ('654200', '650000', '塔城地区', 'Tacheng Prefecture');
INSERT INTO `region` VALUES ('654300', '650000', '阿勒泰地区', 'Altay Prefecture');
INSERT INTO `region` VALUES ('659000', '650000', '可克达拉市', 'Kokdala');
INSERT INTO `region` VALUES ('659001', '650000', '石河子市', 'Shihezi');
INSERT INTO `region` VALUES ('659002', '650000', '阿拉尔市', 'Alar');
INSERT INTO `region` VALUES ('659003', '650000', '图木舒克市', 'Tumxuk');
INSERT INTO `region` VALUES ('659004', '650000', '五家渠市', 'Wujiaqu');
INSERT INTO `region` VALUES ('659005', '650000', '北屯市', 'Beitun');
INSERT INTO `region` VALUES ('659006', '650000', '铁门关市', 'Tiemenguan');
INSERT INTO `region` VALUES ('659007', '650000', '双河市', 'Shuanghe');
INSERT INTO `region` VALUES ('659009', '650000', '昆玉市', 'Kunyu');
INSERT INTO `region` VALUES ('710000', '0', '台湾', 'Taiwan Province');
INSERT INTO `region` VALUES ('710100', '710000', '台北市', 'Taipei');
INSERT INTO `region` VALUES ('710200', '710000', '高雄市', 'Kaohsiung');
INSERT INTO `region` VALUES ('710300', '710000', '台南市', 'Tainan');
INSERT INTO `region` VALUES ('710400', '710000', '台中市', 'Taichung');
INSERT INTO `region` VALUES ('710500', '710000', '金门县', 'Kinmen County');
INSERT INTO `region` VALUES ('710600', '710000', '南投县', 'Nantou County');
INSERT INTO `region` VALUES ('710700', '710000', '基隆市', 'Keelung');
INSERT INTO `region` VALUES ('710800', '710000', '新竹市', 'Hsinchu');
INSERT INTO `region` VALUES ('710900', '710000', '嘉义市', 'Chiayi');
INSERT INTO `region` VALUES ('711100', '710000', '新北市', 'Xinbei');
INSERT INTO `region` VALUES ('711200', '710000', '宜兰县', 'Yilan County');
INSERT INTO `region` VALUES ('711300', '710000', '新竹县', 'Hsinchu County');
INSERT INTO `region` VALUES ('711400', '710000', '桃园市', 'Taoyuan');
INSERT INTO `region` VALUES ('711500', '710000', '苗栗县', 'Miaoli County');
INSERT INTO `region` VALUES ('711700', '710000', '彰化县', 'Zhanghua County');
INSERT INTO `region` VALUES ('711900', '710000', '嘉义县', 'Jiayi County');
INSERT INTO `region` VALUES ('712100', '710000', '云林县', 'Yunlin County');
INSERT INTO `region` VALUES ('712400', '710000', '屏东县', 'Pingdong County');
INSERT INTO `region` VALUES ('712500', '710000', '台东县', 'Taidong County');
INSERT INTO `region` VALUES ('712600', '710000', '花莲县', 'Hualian County');
INSERT INTO `region` VALUES ('712700', '710000', '澎湖县', 'Penghu County');
INSERT INTO `region` VALUES ('712800', '710000', '连江县', 'Lianjiang County');
INSERT INTO `region` VALUES ('810000', '0', '香港特别行政区', 'Hong Kong Special Administrative Region');
INSERT INTO `region` VALUES ('810100', '810000', '香港岛', 'Hong Kong Island');
INSERT INTO `region` VALUES ('810200', '810000', '九龙', 'Kowloon');
INSERT INTO `region` VALUES ('810300', '810000', '新界', 'New Territories');
INSERT INTO `region` VALUES ('820000', '0', '澳门特别行政区', 'Macau Special Administrative Region');
INSERT INTO `region` VALUES ('820100', '820000', '澳门半岛', 'Macau Peninsula');
INSERT INTO `region` VALUES ('820200', '820000', '离岛', 'Macau Islands');
COMMIT;

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account` (
  `uid` bigint(20) NOT NULL,
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `is_lock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否锁定\n0-未锁定\n1-锁定',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除\n0-未删除\n1-删除',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `idx_email` (`email`) USING BTREE,
  KEY `idx_username` (`username`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户账号信息';


-- ----------------------------
-- Table structure for user_profile
-- ----------------------------
DROP TABLE IF EXISTS `user_profile`;
CREATE TABLE `user_profile` (
  `uid` bigint(20) NOT NULL,
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `province` varchar(6) DEFAULT NULL COMMENT '省，关联region_id',
  `city` varchar(6) DEFAULT NULL COMMENT '市，关联region_id',
  `gender` int(1) DEFAULT NULL COMMENT '性别\n0-女\n1-男',
  `introduction` varchar(200) DEFAULT NULL COMMENT '文字介绍',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `updated_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `nickname` (`nickname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基础信息';


SET FOREIGN_KEY_CHECKS = 1;
