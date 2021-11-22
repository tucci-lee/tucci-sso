// 消息总线，组件通信
import mitt from "mitt"

const mittObj = mitt();

/**
 * 广播头像修改
 * @param avatar
 */
export function editAvatar(avatar) {
    mittObj.emit("avatar", avatar);
}

/**
 * 监听头像
 * @param fn
 */
export function listenerAvatar(fn) {
    mittObj.on('avatar', data => fn(data));
}

/**
 * 广播获取账号信息
 */
export function getAccount() {
    mittObj.emit("account", true);
}

/**
 * 监听获取账号信息
 * @param fn
 */
export function listenerAccount(fn) {
    mittObj.on('account', data => fn(data));
}