export interface MockArticle {
  id: string
  title: string
  solution: string
  imageUrl: string
}

export interface MockUser {
  id: string
  username: string
  nickname: string
  userName: string
  role: string
  phone: string
  avatar: string
  imageUrl: string
  sex: string
  age: string
  school: string
  content: string
  tags: string
}

export interface MockPost {
  id: string
  title: string
  content: string
  nickname: string
  imageUrl: string
  tags: string
  type: 'demand' | 'supply'
  duration: number
  date: string
  time: string
  reward: number
  createtime: string
  state: string
}

export interface MockReport {
  id: string
  title: string
  content: string
  type: 'post_report' | 'evaluate_report' | 'user_report' | 'user_evaluate'
  reporternickname: string
  reporteenickname: string
  createtime: string
  imageUrl?: string
  state: string
}

export interface MockFollowRelation {
  follower: string
  follower_image_url: string
  followee: string
  followee_image_url: string
}

const now = () => new Date().toISOString().slice(0, 19).replace('T', ' ')

export const mockState = {
  token: 'mock-token-admin-001',
  loginCaptcha: 'ABCD',
  currentUserNickname: '李晓华',
  users: [
    {
      id: 'u-1',
      username: 'admin',
      nickname: '李晓华',
      userName: '李晓华',
      role: '0',
      phone: '13987654321',
      avatar: '/mock/avatar-li-xiaohua.png',
      imageUrl: 'avatar-li-xiaohua.png',
      sex: '女',
      age: '29',
      school: '华西医院',
      content: '长期关注遗传代谢性肝病管理与随访。',
      tags: '遗传代谢,肝病,随访'
    },
    {
      id: 'u-2',
      username: 'admin2',
      nickname: '张医生',
      userName: '张医生',
      role: '1',
      phone: '13800001111',
      avatar: '/mock/avatar-doctor-zhang.png',
      imageUrl: 'avatar-doctor-zhang.png',
      sex: '男',
      age: '35',
      school: '成都市第一人民医院',
      content: '专注肝豆状核变性与脂代谢异常。',
      tags: '肝豆状核变性,脂代谢'
    },
    {
      id: 'u-3',
      username: 'visitor1',
      nickname: '王同学',
      userName: '王同学',
      role: '0',
      phone: '13700002222',
      avatar: '/mock/avatar-wang.png',
      imageUrl: 'avatar-wang.png',
      sex: '女',
      age: '22',
      school: '四川大学',
      content: '关心罕见病饮食和生活管理。',
      tags: '罕见病,饮食'
    }
  ] as MockUser[],
  articles: [
    {
      id: 'a-1',
      title: '肝豆状核变性（Wilson病）的日常饮食指南',
      solution: '限制铜摄入，避免动物内脏、坚果和贝类，定期复查铜蓝蛋白。',
      imageUrl: 'article-wilson.png'
    },
    {
      id: 'a-2',
      title: '糖原累积病患者如何预防低血糖',
      solution: '采用少量多餐策略，夜间可遵医嘱补充生玉米淀粉，避免空腹剧烈运动。',
      imageUrl: 'article-gsd.png'
    },
    {
      id: 'a-3',
      title: '血色病患者的铁超载管理',
      solution: '定期静脉放血，避免高铁负荷饮食并限制维生素C过量补充。',
      imageUrl: 'article-hemochromatosis.png'
    }
  ] as MockArticle[],
  posts: [
    {
      id: 'p-1',
      title: '求教铜蓝蛋白偏低如何复查',
      content: '近期检查提示CER偏低，想咨询进一步检查流程。',
      nickname: '李晓华',
      imageUrl: 'post-1.png',
      tags: '肝豆状核变性,检查',
      type: 'demand',
      duration: 30,
      date: '2026-04-14',
      time: '10:00',
      reward: 50,
      createtime: now(),
      state: '0'
    },
    {
      id: 'p-2',
      title: '分享低铜饮食清单',
      content: '整理了一个低铜饮食周计划，欢迎交流。',
      nickname: '张医生',
      imageUrl: 'post-2.png',
      tags: '饮食,随访',
      type: 'supply',
      duration: 20,
      date: '2026-04-13',
      time: '18:30',
      reward: 0,
      createtime: now(),
      state: '1'
    }
  ] as MockPost[],
  reports: [
    {
      id: 'r-1',
      title: '帖子疑似错误医疗建议',
      content: '该帖内容未经证实，可能误导患者。',
      type: 'post_report',
      reporternickname: '王同学',
      reporteenickname: '李晓华',
      createtime: now(),
      state: '0'
    },
    {
      id: 'r-2',
      title: '评论存在不当言论',
      content: '评论区有攻击性表达。',
      type: 'evaluate_report',
      reporternickname: '李晓华',
      reporteenickname: '王同学',
      createtime: now(),
      state: '0'
    },
    {
      id: 'r-3',
      title: '用户资料疑似伪造',
      content: '用户信息真实性存疑。',
      type: 'user_report',
      reporternickname: '张医生',
      reporteenickname: '王同学',
      createtime: now(),
      state: '0'
    }
  ] as MockReport[],
  follows: [
    {
      follower: '李晓华',
      follower_image_url: 'avatar-li-xiaohua.png',
      followee: '张医生',
      followee_image_url: 'avatar-doctor-zhang.png'
    }
  ] as MockFollowRelation[],
  collects: [] as { id: string; nickname: string; targetId: string }[],
  likes: [] as { id: string; nickname: string; targetId: string }[]
}

export const getCurrentUser = () => {
  const current = mockState.users.find((user) => user.nickname === mockState.currentUserNickname)
  return current || mockState.users[0]
}

export const nextId = (prefix: string) => `${prefix}-${Date.now()}-${Math.floor(Math.random() * 1000)}`

export const filterByKeyword = <T extends Record<string, any>>(items: T[], keyword: string, fields: (keyof T)[]) => {
  const normalizedKeyword = (keyword || '').trim().toLowerCase()
  if (!normalizedKeyword) {
    return [...items]
  }
  return items.filter((item) => fields.some((field) => String(item[field] || '').toLowerCase().includes(normalizedKeyword)))
}

