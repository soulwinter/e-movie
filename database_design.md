# E-Movie 数据库设计文档



## 表简介

1. User Table：用户信息表，包含用户的基本信息，如用户名、密码、邮箱等。
2. Movie Table：电影信息表，包含电影的基本信息，如成人标志、预算、网站链接、IMDB链接、原始语言、原始标题、简介、热度、海报链接、发布日期、收入、播放时长、状态、标语、标题、平均评分、评分人数和所有工作人员。
3. Genre Table：电影类型表，包含电影的类型信息，如类型名称等。
4. Movie_Genre Table：电影类型表和电影信息表的中间表，用于表示每个电影可能有多个类型，每个类型可能包含多部电影。
5. Company Table：电影公司表，包含电影制作公司的信息，如公司名称等。
6. Movie_Company Table：电影公司表和电影信息表的中间表，用于表示每个电影可能由多个制作公司共同制作。
7. Production_Country Table：国家表，包含国家的信息，如国家名称等。
8. Movie_Country Table：国家表和电影信息表的中间表，用于表示每个电影可能有多个国家参与制作。
9. Vote Table：评分表，包含用户对电影的评分信息，如用户ID、电影ID、评分和时间戳等。
10. Keyword Table：关键词表，包含电影的关键词信息，如关键词名称等。
11. Movie_Keyword Table：关键词表和电影信息表的中间表，用于表示每个电影可能有多个关键词。



## 字段描述

**1. User Table**

| 字段     | 描述     |
| -------- | -------- |
| id       | 用户ID   |
| username | 用户名   |
| password | 密码     |
| email    | 邮箱     |
| deleted  | 是否删除 |
| token    | -        |

**2. Movie Table**

| 字段              | 描述                     |
| ----------------- | ------------------------ |
| id                | 电影ID                   |
| adult             | 是否为限制级             |
| budget            | 预算                     |
| homepage          | 网站链接                 |
| imdb_id           | IMDB ID                  |
| original_language | 原始语言                 |
| original_title    | 原始标题                 |
| overview          | 简介                     |
| popularity        | 热度                     |
| poster_path       | 海报链接                 |
| release_date      | 发布日期                 |
| revenue           | 收入                     |
| runtime           | 播放时长                 |
| status            | 状态                     |
| tagline           | 标语                     |
| title             | 标题                     |
| vote_average      | 平均评分                 |
| vote_count        | 评分人数                 |
| allCrew           | 所有工作人员，以JSON存储 |
| deleted           | 是否删除                 |

**3. Genre Table**

| 字段    | 描述         |
| ------- | ------------ |
| id      | 电影类型ID   |
| name    | 电影类型名称 |
| deleted | 是否删除     |

**4. Movie_Genre Table**

| 字段     | 描述         |
| -------- | ------------ |
| movie_id | 电影ID       |
| genre_id | 电影类型ID   |
| deleted  | 是否删除     |

**5. Company Table**

| 字段    | 描述         |
| ------- | ------------ |
| id      | 电影公司ID   |
| name    | 电影公司名称 |
| deleted | 是否删除     |

**6. Movie_Company Table**

| 字段       | 描述         |
| ---------- | ------------ |
| movie_id   | 电影ID       |
| company_id | 电影公司ID   |
| deleted    | 是否删除     |

**7. Production_Country Table**

| 字段    | 描述     |
| ------- | -------- |
| id      | 国家ID   |
| name    | 国家名称 |
| deleted | 是否删除 |

**8. Movie_Country Table**

| 字段       | 描述         |
| ---------- | ------------ |
| movie_id   | 电影ID       |
| country_id | 国家ID       |
| deleted    | 是否删除     |

**9. Vote Table**

| 字段      | 描述     |
| --------- | -------- |
| user_id   | 用户ID   |
| movie_id  | 电影ID   |
| rating    | 评分     |
| timestamp | 时间戳   |
| deleted   | 是否删除 |

**10. Keyword Table**

| 字段    | 描述       |
| ------- | ---------- |
| id      | 关键词ID   |
| name    | 关键词名称 |
| deleted | 是否删除   |

**11. Movie_Keyword Table**

| 字段       | 描述           |
| ---------- | -------------- |
| movie_id   | 电影ID         |
| keyword_id | 关键词ID       |
| deleted    | 是否删除       |
