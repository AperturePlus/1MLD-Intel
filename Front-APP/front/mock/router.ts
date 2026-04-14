import { ApiResponse, MockHandler, MockRequestContext } from '@/types/api'
import { filterByKeyword, getCurrentUser, mockState, nextId } from '@/mock/data'

interface MockRoute {
  method: string
  match: RegExp
  handler: MockHandler
}

const success = <T>(data: T, msg = 'success'): ApiResponse<T> => ({
  code: 200,
  msg,
  data
})

const failure = (msg = 'mock request failed', code = 500): ApiResponse<null> => ({
  code,
  msg,
  data: null
})

const getStringValue = (context: MockRequestContext, key: string, fallback = '') =>
  String((context.data && context.data[key]) || fallback).trim()

const getRole = (context: MockRequestContext) => String((context.data && context.data.role) || '0')

const routes: MockRoute[] = [
  {
    method: 'GET',
    match: /^\/captchaImage$/,
    handler: () =>
      success({
        captchaEnabled: true,
        data: 'iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO2YJwkAAAAASUVORK5CYII=',
        uuid: `captcha-${Date.now()}`
      })
  },
  {
    method: 'GET',
    match: /^\/user\/login$/,
    handler: (context) => {
      const username = getStringValue(context, 'username')
      const password = getStringValue(context, 'password')
      const role = getRole(context)
      if (!username || !password) {
        return failure('账号或密码不能为空')
      }
      const targetUser = mockState.users.find((user) => user.username === username) || mockState.users[0]
      mockState.currentUserNickname = targetUser.nickname
      mockState.token = `mock-token-${username}-${Date.now()}`
      return {
        code: 200,
        msg: mockState.token,
        data: {
          token: mockState.token,
          role
        }
      }
    }
  },
  {
    method: 'POST',
    match: /^\/user\/register$/,
    handler: (context) => {
      const username = getStringValue(context, 'username')
      const password = getStringValue(context, 'password')
      if (!username || !password) {
        return failure('账号或密码不能为空')
      }
      const exists = mockState.users.some((user) => user.username === username)
      if (exists) {
        return failure('账号已存在')
      }
      const userId = nextId('u')
      mockState.users.push({
        id: userId,
        username,
        nickname: username,
        userName: username,
        role: '0',
        phone: '',
        avatar: '/mock/avatar-default.png',
        imageUrl: 'avatar-default.png',
        sex: '',
        age: '',
        school: '',
        content: '',
        tags: ''
      })
      return success({ id: userId }, '注册成功')
    }
  },
  {
    method: 'POST',
    match: /^\/user\/password\/send-code$/,
    handler: (context) => {
      const phone = getStringValue(context, 'phone')
      if (!phone) {
        return failure('手机号不能为空')
      }
      return success(
        {
          sent: true,
          code: '1234',
          expiresInSeconds: 300
        },
        '验证码已发送'
      )
    }
  },
  {
    method: 'POST',
    match: /^\/user\/password\/verify-code$/,
    handler: (context) => {
      const phone = getStringValue(context, 'phone')
      const code = getStringValue(context, 'code')
      if (!phone || !code) {
        return failure('手机号或验证码不能为空')
      }
      return success({ verified: true }, '验证通过')
    }
  },
  {
    method: 'POST',
    match: /^\/user\/password\/reset$/,
    handler: (context) => {
      const newPassword = getStringValue(context, 'newPassword')
      const confirmPassword = getStringValue(context, 'confirmPassword')
      if (!newPassword || !confirmPassword) {
        return failure('新密码不能为空')
      }
      if (newPassword !== confirmPassword) {
        return failure('两次输入的密码不一致')
      }
      return success(true, '密码重置成功')
    }
  },
  {
    method: 'POST',
    match: /^\/logout$/,
    handler: () => success(true, '退出成功')
  },
  {
    method: 'GET',
    match: /^\/user$/,
    handler: () => {
      const currentUser = getCurrentUser()
      return {
        code: 200,
        msg: 'success',
        user: {
          userName: currentUser.nickname,
          avatar: currentUser.avatar
        },
        roles: currentUser.role === '1' ? ['ROLE_ADMIN'] : ['ROLE_DEFAULT'],
        permissions: ['*:*:*'],
        data: {
          ...currentUser
        }
      } as any
    }
  },
  {
    method: 'PUT',
    match: /^\/user$/,
    handler: (context) => {
      const currentUser = getCurrentUser()
      const target = mockState.users.find((item) => item.id === currentUser.id)
      if (!target) {
        return failure('用户不存在')
      }
      const fields = ['phone', 'sex', 'age', 'school', 'content', 'imageUrl', 'nickname', 'tags']
      fields.forEach((field) => {
        if (context.data[field] !== undefined) {
          target[field as keyof typeof target] = String(context.data[field] || '')
        }
      })
      if (target.imageUrl) {
        target.avatar = `/mock/${target.imageUrl}`
      }
      return success(target, '更新成功')
    }
  },
  {
    method: 'GET',
    match: /^\/user\/updatePassword$/,
    handler: () => success(true, '修改成功')
  },
  {
    method: 'GET',
    match: /^\/user\/setDiet$/,
    handler: () => success(true, '设置成功')
  },
  {
    method: 'POST',
    match: /^\/user\/list\/.*$/,
    handler: (context) => {
      const keyword = getStringValue(context, 'param')
      const users = filterByKeyword(mockState.users, keyword, ['nickname', 'school', 'tags'])
      return success(users)
    }
  },
  {
    method: 'GET',
    match: /^\/user\/five$/,
    handler: (context) => {
      const keyword = getStringValue(context, 'param')
      const users = filterByKeyword(mockState.users, keyword, ['nickname', 'school', 'tags']).slice(0, 5)
      return success(users)
    }
  },
  {
    method: 'GET',
    match: /^\/user\/one$/,
    handler: (context) => {
      const nickname = getStringValue(context, 'param') || getStringValue(context, 'nickname')
      const target = mockState.users.find((user) => user.nickname === nickname)
      if (!target) {
        return failure('用户不存在', 404)
      }
      return success(target)
    }
  },
  {
    method: 'GET',
    match: /^\/user\/adminList$/,
    handler: (context) => {
      const keyword = getStringValue(context, 'keyword')
      const users = filterByKeyword(mockState.users, keyword, ['nickname', 'phone'])
      return success(users)
    }
  },
  {
    method: 'POST',
    match: /^\/user\/[^/]+\/(ban|unban)$/,
    handler: (context) => {
      const matched = context.path.match(/^\/user\/([^/]+)\/(ban|unban)$/)
      if (!matched) {
        return failure('非法请求')
      }
      const [, userId, action] = matched
      const target = mockState.users.find((user) => user.id === userId)
      if (!target) {
        return failure('用户不存在')
      }
      target.role = action === 'ban' ? '2' : '0'
      return success(target)
    }
  },
  {
    method: 'POST',
    match: /^\/advice\/list\/.*$/,
    handler: (context) => {
      const keyword = getStringValue(context, 'param')
      return success(filterByKeyword(mockState.articles, keyword, ['title', 'solution']))
    }
  },
  {
    method: 'GET',
    match: /^\/advice\/five$/,
    handler: (context) => {
      const keyword = getStringValue(context, 'param')
      return success(filterByKeyword(mockState.articles, keyword, ['title', 'solution']).slice(0, 5))
    }
  },
  {
    method: 'DELETE',
    match: /^\/advice\/delete\/.+$/,
    handler: (context) => {
      const id = context.path.split('/').pop() || ''
      mockState.articles = mockState.articles.filter((article) => article.id !== id)
      return success(true, '删除成功')
    }
  },
  {
    method: 'POST',
    match: /^\/advice\/add$/,
    handler: (context) => {
      const id = nextId('a')
      const article = {
        id,
        title: getStringValue(context, 'title'),
        solution: getStringValue(context, 'solution'),
        imageUrl: getStringValue(context, 'imageUrl')
      }
      mockState.articles.unshift(article)
      return success(article, '发布成功')
    }
  },
  {
    method: 'PUT',
    match: /^\/advice\/update$/,
    handler: (context) => {
      const id = getStringValue(context, 'id')
      const target = mockState.articles.find((article) => article.id === id)
      if (!target) {
        return failure('资讯不存在')
      }
      target.title = getStringValue(context, 'title', target.title)
      target.solution = getStringValue(context, 'solution', target.solution)
      target.imageUrl = getStringValue(context, 'imageUrl', target.imageUrl)
      return success(target, '更新成功')
    }
  },
  {
    method: 'POST',
    match: /^\/post\/add$/,
    handler: (context) => {
      const newPost = {
        id: nextId('p'),
        title: getStringValue(context, 'title'),
        content: getStringValue(context, 'content'),
        nickname: getStringValue(context, 'nickname', getCurrentUser().nickname),
        imageUrl: getStringValue(context, 'imageUrl'),
        tags: getStringValue(context, 'tags'),
        type: getStringValue(context, 'type', 'demand') as 'demand' | 'supply',
        duration: Number(context.data.duration || 30),
        date: getStringValue(context, 'date', '2026-04-14'),
        time: getStringValue(context, 'time', '09:00'),
        reward: Number(context.data.reward || 0),
        createtime: getStringValue(context, 'createtime', new Date().toISOString().slice(0, 19).replace('T', ' ')),
        state: '0'
      }
      mockState.posts.unshift(newPost)
      return success(newPost, '发布成功')
    }
  },
  {
    method: 'GET',
    match: /^\/post\/five$/,
    handler: (context) => {
      const keyword = getStringValue(context, 'param')
      return success(filterByKeyword(mockState.posts, keyword, ['title', 'content', 'nickname']).slice(0, 5))
    }
  },
  {
    method: 'GET',
    match: /^\/post\/my$/,
    handler: (context) => {
      const nickname = getStringValue(context, 'nickname', getCurrentUser().nickname)
      return success(mockState.posts.filter((post) => post.nickname === nickname))
    }
  },
  {
    method: 'DELETE',
    match: /^\/post\/delete\/.+$/,
    handler: (context) => {
      const id = context.path.split('/').pop() || ''
      mockState.posts = mockState.posts.filter((post) => post.id !== id)
      return success(true, '删除成功')
    }
  },
  {
    method: 'GET',
    match: /^\/post\/pending$/,
    handler: () => success(mockState.posts.filter((post) => post.state === '0'))
  },
  {
    method: 'POST',
    match: /^\/post\/review$/,
    handler: (context) => {
      const id = getStringValue(context, 'id')
      const state = getStringValue(context, 'state')
      const target = mockState.posts.find((post) => post.id === id)
      if (!target) {
        return failure('帖子不存在')
      }
      target.state = state || '1'
      return success(target, '审核成功')
    }
  },
  {
    method: 'GET',
    match: /^\/followship\/followees$/,
    handler: (context) => {
      const follower = getStringValue(context, 'follower')
      return success(mockState.follows.filter((item) => item.follower === follower))
    }
  },
  {
    method: 'GET',
    match: /^\/followship\/followers$/,
    handler: (context) => {
      const followee = getStringValue(context, 'followee')
      return success(mockState.follows.filter((item) => item.followee === followee))
    }
  },
  {
    method: 'POST',
    match: /^\/followship\/add$/,
    handler: (context) => {
      const follower = getStringValue(context, 'follower')
      const followee = getStringValue(context, 'followee')
      const exists = mockState.follows.some((item) => item.follower === follower && item.followee === followee)
      if (!exists && follower && followee) {
        mockState.follows.push({
          follower,
          follower_image_url: getStringValue(context, 'follower_image_url'),
          followee,
          followee_image_url: getStringValue(context, 'followee_image_url')
        })
      }
      return success(true, '关注成功')
    }
  },
  {
    method: 'POST',
    match: /^\/followship\/delete$/,
    handler: (context) => {
      const follower = getStringValue(context, 'follower')
      const followee = getStringValue(context, 'followee')
      mockState.follows = mockState.follows.filter((item) => !(item.follower === follower && item.followee === followee))
      return success(true, '取消关注成功')
    }
  },
  {
    method: 'GET',
    match: /^\/followship\/check$/,
    handler: (context) => {
      const follower = getStringValue(context, 'follower')
      const followee = getStringValue(context, 'followee')
      const isFollowing = mockState.follows.some((item) => item.follower === follower && item.followee === followee)
      return success({ isFollowing })
    }
  },
  {
    method: 'POST',
    match: /^\/report\/add$/,
    handler: (context) => {
      const report = {
        id: nextId('r'),
        title: getStringValue(context, 'title'),
        content: getStringValue(context, 'content'),
        type: getStringValue(context, 'type', 'user_report') as any,
        reporternickname: getStringValue(context, 'reporternickname', getCurrentUser().nickname),
        reporteenickname: getStringValue(context, 'reporteenickname'),
        createtime: getStringValue(context, 'createtime', new Date().toISOString().slice(0, 19).replace('T', ' ')),
        state: getStringValue(context, 'state', '0'),
        imageUrl: getStringValue(context, 'imageUrl')
      }
      mockState.reports.unshift(report)
      return success(report, '提交成功')
    }
  },
  {
    method: 'GET',
    match: /^\/report\/all_pending$/,
    handler: () => success(mockState.reports.filter((report) => report.state === '0'))
  },
  {
    method: 'POST',
    match: /^\/report\/update_state$/,
    handler: (context) => {
      const id = getStringValue(context, 'id')
      const state = getStringValue(context, 'state')
      const target = mockState.reports.find((report) => report.id === id)
      if (!target) {
        return failure('举报记录不存在')
      }
      target.state = state || '1'
      return success(target, '处理成功')
    }
  },
  {
    method: 'GET',
    match: /^\/report\/my_user_evaluate$/,
    handler: (context) => {
      const nickname = getStringValue(context, 'param')
      return success(mockState.reports.filter((report) => report.reporteenickname === nickname && report.type === 'user_evaluate'))
    }
  },
  {
    method: 'GET',
    match: /^\/report\/my_evaluate$/,
    handler: (context) => {
      const nickname = getStringValue(context, 'param')
      return success(mockState.reports.filter((report) => report.reporternickname === nickname && report.type === 'user_evaluate'))
    }
  },
  {
    method: 'POST',
    match: /^\/report\/delete_evaluate$/,
    handler: (context) => {
      const id = getStringValue(context, 'id')
      mockState.reports = mockState.reports.filter((report) => report.id !== id)
      return success(true, '删除成功')
    }
  },
  {
    method: 'GET',
    match: /^\/report\/my_report$/,
    handler: (context) => {
      const nickname = getStringValue(context, 'nickname')
      return success(mockState.reports.filter((report) => report.reporternickname === nickname || report.reporteenickname === nickname))
    }
  },
  {
    method: 'GET',
    match: /^\/report\/my_collect$/,
    handler: (context) => {
      const nickname = getStringValue(context, 'nickname')
      return success(mockState.collects.filter((collect) => collect.nickname === nickname))
    }
  },
  {
    method: 'DELETE',
    match: /^\/report\/delete_collect$/,
    handler: (context) => {
      const id = getStringValue(context, 'id')
      mockState.collects = mockState.collects.filter((collect) => collect.id !== id)
      return success(true, '删除成功')
    }
  },
  {
    method: 'GET',
    match: /^\/collects\/collects\/list$/,
    handler: () => success(mockState.collects)
  },
  {
    method: 'POST',
    match: /^\/collects\/collects$/,
    handler: (context) => {
      const collect = {
        id: nextId('c'),
        nickname: getStringValue(context, 'nickname', getCurrentUser().nickname),
        targetId: getStringValue(context, 'targetId')
      }
      mockState.collects.push(collect)
      return success(collect)
    }
  },
  {
    method: 'DELETE',
    match: /^\/collects\/collects\/.+$/,
    handler: (context) => {
      const id = context.path.split('/').pop() || ''
      mockState.collects = mockState.collects.filter((collect) => collect.id !== id)
      return success(true)
    }
  },
  {
    method: 'GET',
    match: /^\/likes\/likes\/list$/,
    handler: () => success(mockState.likes)
  },
  {
    method: 'POST',
    match: /^\/likes\/likes$/,
    handler: (context) => {
      const like = {
        id: nextId('l'),
        nickname: getStringValue(context, 'nickname', getCurrentUser().nickname),
        targetId: getStringValue(context, 'targetId')
      }
      mockState.likes.push(like)
      return success(like)
    }
  },
  {
    method: 'DELETE',
    match: /^\/likes\/likes\/.+$/,
    handler: (context) => {
      const id = context.path.split('/').pop() || ''
      mockState.likes = mockState.likes.filter((like) => like.id !== id)
      return success(true)
    }
  },
  {
    method: 'GET',
    match: /^\/api\/v1\/app\/identity\/auth\/appeal-contact$/,
    handler: () =>
      success({
        phone: '028-12345678',
        email: 'imld-support@example.com'
      })
  },
  {
    method: 'POST',
    match: /^\/api\/chat$/,
    handler: (context) => {
      const messages = Array.isArray(context.data.messages) ? context.data.messages : []
      const lastUserMessage = messages
        .slice()
        .reverse()
        .find((item: any) => item.role === 'user')
      const reply = lastUserMessage
        ? `已收到：${lastUserMessage.content}。这是 Mock AI 回复，用于离线联调。`
        : '您好，这是 Mock AI 助手。'
      return success({
        reply
      })
    }
  },
  {
    method: 'POST',
    match: /^\/assessment\/submit$/,
    handler: (context) =>
      success(
        {
          assessmentId: nextId('asmt'),
          receivedAt: new Date().toISOString(),
          payload: context.data
        },
        '提交成功'
      )
  },
  {
    method: 'GET',
    match: /^\/recipe\/daily$/,
    handler: () =>
      success({
        breakfast: [
          {
            name: '麦淀粉煎饼 (100g)',
            desc: '提供充足碳水化合物，且几乎不含非优质植物蛋白，减轻肝脏代谢负担。',
            img: 'https://images.unsplash.com/photo-1528207776546-365bb710ee93?w=400&q=80',
            tags: ['高热量', '极低蛋白']
          },
          {
            name: '水煮蛋清 (2个)',
            desc: '弃去富含磷的蛋黄，仅保留蛋清，补充人体必需的高生物价优质蛋白。',
            img: 'https://images.unsplash.com/photo-1516684732162-798a0062be99?w=400&q=80',
            tags: ['优质蛋白', '低铜']
          }
        ],
        lunch: [
          {
            name: '清蒸鲈鱼 (50g)',
            desc: '优选淡水白肉鱼，控制分量，提供优质蛋白及 Omega-3 脂肪酸。',
            img: 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400&q=80',
            tags: ['优质低蛋白', '易消化']
          },
          {
            name: '水煮焯水冬瓜 (150g)',
            desc: '冬瓜利尿消肿且含铜极低。烹饪前焯烫去汤以进一步减少矿物质。',
            img: 'https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400&q=80',
            tags: ['低铜', '利尿消肿']
          },
          {
            name: '低蛋白大米饭 (1碗)',
            desc: '特制低蛋白大米，口感软糯，帮助在控制蛋白的同时吃饱吃好。',
            img: 'https://images.unsplash.com/photo-1536304929831-ee1ca9d44906?w=400&q=80',
            tags: ['主食替换']
          }
        ],
        dinner: [
          {
            name: '蒜蓉炒西葫芦 (150g)',
            desc: '西葫芦属于低铜瓜果类蔬菜，适合遗传代谢性肝病患者。',
            img: 'https://images.unsplash.com/photo-1547592180-85f173990554?w=400&q=80',
            tags: ['低铜低磷', '高膳食纤维']
          },
          {
            name: '纯藕粉羹 (1碗)',
            desc: '作为晚餐主食，藕粉几乎不含蛋白质，且能提供饱腹感和热量。',
            img: 'https://images.unsplash.com/photo-1582515073490-39981397c445?w=400&q=80',
            tags: ['零蛋白', '护肝淀粉']
          }
        ]
      })
  },
  {
    method: 'GET',
    match: /^\/assessment\/result$/,
    handler: () =>
      success({
        probability: 88.5,
        riskLevel: '高风险',
        riskDescription: '您的指标提示遗传代谢性肝病风险较高，建议尽快到专科门诊进一步评估。',
        suspectedDisease: {
          name: '肝豆状核变性 (Wilson病)',
          gene: 'ATP7B 基因突变'
        },
        keyFactors: [
          { name: '谷丙转氨酶 (ALT)', value: '85 U/L', contribution: 35.2, color: '#fa3534', remark: '显著高于正常区间' },
          { name: '铜蓝蛋白 (CER)', value: '0.12 g/L', contribution: 28.7, color: '#ff9900', remark: '明显偏低' },
          { name: '总胆红素 (TBIL)', value: '25.4 μmol/L', contribution: 21.0, color: '#ff9900', remark: '轻度升高' }
        ],
        advices: [
          '尽快前往三甲医院消化内科或肝病专科门诊就诊。',
          '建议进行24小时尿铜与裂隙灯K-F环检查。',
          '短期内严格限制高铜食物摄入。',
          '保持规律随访并记录症状变化。'
        ]
      })
  }
]

export const dispatchMockRequest = async (context: MockRequestContext) => {
  const route = routes.find((item) => item.method === context.method && item.match.test(context.path))
  if (!route) {
    return failure(`mock route not found: ${context.method} ${context.path}`, 404)
  }
  return route.handler(context)
}
