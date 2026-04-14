import request from '@/utils/request'

export function AddPost(data) {
  return request({
    url: '/post/add',
    method: 'post',
    data
  })
}

export function getPost(params) {
  return request({
    url: '/post/five',
    method: 'get',
	params
  })
}

export function getMyPosts(nickname) {
  return request({
    url: '/post/my',
    method: 'get',
    params: { nickname }
  })
}

export function deletePostById(id) {
  return request({
    url: `/post/delete/${id}`,
    method: 'delete'
  })
}

// 获取待审核帖子（state = 0）
export function getPendingPosts() {
  return request({
    url: '/post/pending',
    method: 'GET'
  })
}

// 审核帖子：通过或不通过
export function reviewPostById(id, state) {
  return request({
    url: '/post/review',
    method: 'POST',
    data: {
      id: id.toString(),         // 👈 强制转成字符串
      state: state.toString()
    }
  })
}
