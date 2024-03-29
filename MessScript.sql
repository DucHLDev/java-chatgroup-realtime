USE [MessLibrary]
GO
/****** Object:  Table [dbo].[UserInfo]    Script Date: 06/03/2022 19:15:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserInfo](
	[HoTen] [nvarchar](50) NULL,
	[GioiTinh] [nvarchar](50) NULL,
	[NgaySinh] [nvarchar](50) NULL,
	[SDT] [nvarchar](50) NULL,
	[UserName] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](50) NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Mess]    Script Date: 06/03/2022 19:15:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mess](
	[ID] [numeric](10, 0) IDENTITY(1,1) NOT NULL,
	[Time] [nvarchar](50) NULL,
	[UserName] [nvarchar](50) NULL,
	[Body] [nvarchar](max) NULL,
 CONSTRAINT [PK_Mess] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  ForeignKey [FK_Mess_User]    Script Date: 06/03/2022 19:15:13 ******/
ALTER TABLE [dbo].[Mess]  WITH CHECK ADD  CONSTRAINT [FK_Mess_User] FOREIGN KEY([UserName])
REFERENCES [dbo].[UserInfo] ([UserName])
GO
ALTER TABLE [dbo].[Mess] CHECK CONSTRAINT [FK_Mess_User]
GO
