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
    },
    {
      id: 'u-4',
      username: 'patient01',
      nickname: '陈婷婷',
      userName: '陈婷婷',
      role: '0',
      phone: '13512345678',
      avatar: '/mock/avatar-chen.png',
      imageUrl: 'avatar-chen.png',
      sex: '女',
      age: '26',
      school: '重庆医科大学附属第一医院',
      content: '确诊Wilson病三年，目前口服青霉胺治疗中，希望交流低铜饮食经验。',
      tags: '肝豆状核变性,Wilson病,低铜饮食'
    },
    {
      id: 'u-5',
      username: 'doctor02',
      nickname: '刘主任',
      userName: '刘主任',
      role: '1',
      phone: '13611112222',
      avatar: '/mock/avatar-liu.png',
      imageUrl: 'avatar-liu.png',
      sex: '男',
      age: '48',
      school: '北京协和医院',
      content: '消化内科主任医师，长期从事遗传代谢性肝病的临床与基础研究。',
      tags: '消化内科,遗传代谢,临床研究'
    },
    {
      id: 'u-6',
      username: 'patient02',
      nickname: '赵明远',
      userName: '赵明远',
      role: '0',
      phone: '13722223333',
      avatar: '/mock/avatar-zhao.png',
      imageUrl: 'avatar-zhao.png',
      sex: '男',
      age: '34',
      school: '四川省人民医院',
      content: '糖原累积病Ia型患者，关注饮食管理与运动指导。',
      tags: '糖原累积病,GSD,饮食管理'
    },
    {
      id: 'u-7',
      username: 'family01',
      nickname: '周芸',
      userName: '周芸',
      role: '0',
      phone: '13833334444',
      avatar: '/mock/avatar-zhou.png',
      imageUrl: 'avatar-zhou.png',
      sex: '女',
      age: '42',
      school: '成都市妇女儿童中心医院',
      content: '孩子确诊酪氨酸血症I型，作为家属希望了解更多疾病知识和护理方法。',
      tags: '酪氨酸血症,家属,儿童护理'
    },
    {
      id: 'u-8',
      username: 'patient03',
      nickname: '孙强',
      userName: '孙强',
      role: '0',
      phone: '13944445555',
      avatar: '/mock/avatar-sun.png',
      imageUrl: 'avatar-sun.png',
      sex: '男',
      age: '51',
      school: '西南医科大学附属医院',
      content: '遗传性血色病患者，定期静脉放血治疗中，关注铁超载管理。',
      tags: '血色病,铁超载,放血治疗'
    },
    {
      id: 'u-9',
      username: 'doctor03',
      nickname: '林医生',
      userName: '林医生',
      role: '1',
      phone: '13055556666',
      avatar: '/mock/avatar-lin.png',
      imageUrl: 'avatar-lin.png',
      sex: '女',
      age: '38',
      school: '华西医院',
      content: '肝病科副主任医师，擅长遗传性肝病基因诊断与个体化治疗方案制定。',
      tags: '肝病科,基因诊断,个体化治疗'
    },
    {
      id: 'u-10',
      username: 'patient04',
      nickname: '吴佳琪',
      userName: '吴佳琪',
      role: '0',
      phone: '13166667777',
      avatar: '/mock/avatar-wu.png',
      imageUrl: 'avatar-wu.png',
      sex: '女',
      age: '19',
      school: '四川大学',
      content: '尿素循环障碍患者，大学在读，关注低蛋白饮食与日常学习生活平衡。',
      tags: '尿素循环障碍,低蛋白饮食,大学生'
    }
  ] as MockUser[],
  articles: [
    {
      id: 'a-1',
      title: '肝豆状核变性（Wilson病）的日常饮食指南',
      solution: '限制铜摄入，避免动物内脏、坚果和贝类，定期复查铜蓝蛋白。推荐食用低铜食物如大米、面条、鸡蛋清等。',
      imageUrl: 'article-wilson.png'
    },
    {
      id: 'a-2',
      title: '糖原累积病患者如何预防低血糖',
      solution: '采用少量多餐策略，夜间可遵医嘱补充生玉米淀粉，避免空腹剧烈运动。建议每3-4小时进食一次。',
      imageUrl: 'article-gsd.png'
    },
    {
      id: 'a-3',
      title: '血色病患者的铁超载管理',
      solution: '定期静脉放血，避免高铁负荷饮食并限制维生素C过量补充。建议每年监测血清铁蛋白和转铁蛋白饱和度。',
      imageUrl: 'article-hemochromatosis.png'
    },
    {
      id: 'a-4',
      title: '酪氨酸血症I型的新生儿筛查与早期干预',
      solution: '通过新生儿足跟血筛查检测琥珀酰丙酮水平，阳性患儿应尽早启动尼替西农（NTBC）治疗并配合低酪氨酸/苯丙氨酸饮食。',
      imageUrl: 'article-tyrosinemia.png'
    },
    {
      id: 'a-5',
      title: '尿素循环障碍患者的蛋白质摄入管理',
      solution: '严格控制天然蛋白质摄入量，必要时补充必需氨基酸混合物。急性期需紧急处理高氨血症，避免长时间禁食。',
      imageUrl: 'article-ucd.png'
    },
    {
      id: 'a-6',
      title: '遗传代谢性肝病患者的运动建议',
      solution: '推荐低至中强度的有氧运动如散步、太极拳，避免高强度无氧运动。运动前后注意血糖监测，糖原累积病患者尤需注意。',
      imageUrl: 'article-exercise.png'
    },
    {
      id: 'a-7',
      title: 'K-F角膜环：肝豆状核变性的重要诊断线索',
      solution: 'K-F环是铜沉积在角膜后弹力层形成的金黄色或棕绿色环，裂隙灯检查即可发现。约95%的神经型Wilson病患者可见，是重要的临床体征。',
      imageUrl: 'article-kf-ring.png'
    },
    {
      id: 'a-8',
      title: '遗传代谢性肝病的基因检测指南',
      solution: '全外显子测序（WES）是目前最常用的基因诊断方法，可覆盖绝大部分已知致病基因。建议在专业遗传咨询师指导下解读报告。',
      imageUrl: 'article-genetic.png'
    },
    {
      id: 'a-9',
      title: '青霉胺治疗Wilson病的注意事项',
      solution: '青霉胺是驱铜治疗的经典药物，需从小剂量开始逐步加量。治疗期间定期监测血常规和尿常规，注意过敏反应和骨髓抑制等副作用。',
      imageUrl: 'article-penicillamine.png'
    },
    {
      id: 'a-10',
      title: '遗传代谢性肝病患者的心理健康关注',
      solution: '慢性疾病可能导致焦虑和抑郁情绪，建议患者及家属积极寻求心理支持。加入病友互助群、定期与医生沟通病情进展有助于缓解心理压力。',
      imageUrl: 'article-mental.png'
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
      content: '该帖内容未经证实，可能误导患者。建议核实后再发布。',
      type: 'post_report',
      reporternickname: '王同学',
      reporteenickname: '李晓华',
      createtime: now(),
      state: '0'
    },
    {
      id: 'r-2',
      title: '评论存在不当言论',
      content: '评论区有攻击性表达，影响社区氛围。',
      type: 'evaluate_report',
      reporternickname: '李晓华',
      reporteenickname: '王同学',
      createtime: now(),
      state: '0'
    },
    {
      id: 'r-3',
      title: '用户资料疑似伪造',
      content: '用户信息真实性存疑，头像和简介不匹配。',
      type: 'user_report',
      reporternickname: '张医生',
      reporteenickname: '王同学',
      createtime: now(),
      state: '0'
    },
    {
      id: 'r-4',
      title: '陈婷婷对刘主任的评价',
      content: '刘主任非常专业耐心，解答了我很多关于青霉胺治疗的疑问，非常感谢！',
      type: 'user_evaluate',
      reporternickname: '陈婷婷',
      reporteenickname: '刘主任',
      createtime: now(),
      state: '1'
    },
    {
      id: 'r-5',
      title: '赵明远对林医生的评价',
      content: '林医生帮我制定了个体化的饮食方案，低血糖发作明显减少了。',
      type: 'user_evaluate',
      reporternickname: '赵明远',
      reporteenickname: '林医生',
      createtime: now(),
      state: '1'
    },
    {
      id: 'r-6',
      title: '李晓华对张医生的评价',
      content: '张医生对肝豆状核变性的了解非常深入，给出的饮食建议很实用。',
      type: 'user_evaluate',
      reporternickname: '李晓华',
      reporteenickname: '张医生',
      createtime: now(),
      state: '1'
    }
  ] as MockReport[],
  follows: [
    {
      follower: '李晓华',
      follower_image_url: 'avatar-li-xiaohua.png',
      followee: '张医生',
      followee_image_url: 'avatar-doctor-zhang.png'
    },
    {
      follower: '李晓华',
      follower_image_url: 'avatar-li-xiaohua.png',
      followee: '刘主任',
      followee_image_url: 'avatar-liu.png'
    },
    {
      follower: '李晓华',
      follower_image_url: 'avatar-li-xiaohua.png',
      followee: '林医生',
      followee_image_url: 'avatar-lin.png'
    },
    {
      follower: '陈婷婷',
      follower_image_url: 'avatar-chen.png',
      followee: '张医生',
      followee_image_url: 'avatar-doctor-zhang.png'
    },
    {
      follower: '陈婷婷',
      follower_image_url: 'avatar-chen.png',
      followee: '李晓华',
      followee_image_url: 'avatar-li-xiaohua.png'
    },
    {
      follower: '王同学',
      follower_image_url: 'avatar-wang.png',
      followee: '刘主任',
      followee_image_url: 'avatar-liu.png'
    },
    {
      follower: '赵明远',
      follower_image_url: 'avatar-zhao.png',
      followee: '林医生',
      followee_image_url: 'avatar-lin.png'
    }
  ] as MockFollowRelation[],
  collects: [
    { id: 'c-1', nickname: '李晓华', targetId: 'a-1' },
    { id: 'c-2', nickname: '李晓华', targetId: 'a-7' },
    { id: 'c-3', nickname: '李晓华', targetId: 'a-9' },
    { id: 'c-4', nickname: '陈婷婷', targetId: 'a-1' },
    { id: 'c-5', nickname: '陈婷婷', targetId: 'a-9' },
    { id: 'c-6', nickname: '赵明远', targetId: 'a-2' },
    { id: 'c-7', nickname: '赵明远', targetId: 'a-6' },
    { id: 'c-8', nickname: '王同学', targetId: 'a-5' }
  ] as { id: string; nickname: string; targetId: string }[],
  likes: [
    { id: 'l-1', nickname: '李晓华', targetId: 'a-1' },
    { id: 'l-2', nickname: '李晓华', targetId: 'a-3' },
    { id: 'l-3', nickname: '陈婷婷', targetId: 'a-1' },
    { id: 'l-4', nickname: '陈婷婷', targetId: 'a-7' },
    { id: 'l-5', nickname: '王同学', targetId: 'a-2' },
    { id: 'l-6', nickname: '赵明远', targetId: 'a-2' },
    { id: 'l-7', nickname: '赵明远', targetId: 'a-6' },
    { id: 'l-8', nickname: '孙强', targetId: 'a-3' },
    { id: 'l-9', nickname: '吴佳琪', targetId: 'a-5' }
  ] as { id: string; nickname: string; targetId: string }[]
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

