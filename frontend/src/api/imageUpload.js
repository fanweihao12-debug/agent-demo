import { ElMessage } from 'element-plus'
import api from "../Utils/request.js";

export async function uploadImage(file) {
    if (!file) return null

    // 1. 校验文件类型
    if (!file.type.startsWith('image/')) {
        ElMessage.error('只能上传图片文件')
        return null
    }

    // 2. 校验大小
    const maxSize = 2 * 1024 * 1024
    if (file.size > maxSize) {
        ElMessage.error('图片大小不能超过 2MB')
        return null
    }

    // 3. 构造 FormData
    const formData = new FormData()
    formData.append('file', file)

    try {
        const res = await api.post('/common/image', formData)

        const { code, msg, data } = res.data

        if (code === 1) {
            return data // 返回后端给你的图片 URL
        }

        ElMessage.error(msg || '图片上传失败')
        return null
    } catch (error) {
        console.error(error)
        ElMessage.error('图片上传失败')
        return null
    }
}