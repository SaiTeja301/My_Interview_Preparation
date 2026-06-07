# Claude Certification — Module 2: Claude 101
## Meet Claude, Organize Your Work, Expand Claude's Reach
**Elaborated Study Notes | Certification & Interview Preparation**
> Source: Claude_101 Module 2 | Architect-Grade Reference Notes | Created: May 2026

---

> **🏛️ Architect's Note:**
> Module 1 gave you the **WHY** (AI Fluency Framework). Module 2 gives you the **WHAT** and **HOW** — what Claude actually IS, how it's accessed, how it's organized (Projects, Artifacts, Skills), and how to extend its reach (Connectors, Enterprise Search, Research). Think of Module 2 as the **OPERATOR'S MANUAL** for Claude. If Module 1 was about mindset, Module 2 is about mastery.

---

## Section 1 — Course Overview

**Module Title:** Claude 101 — Meet Claude, Organize Your Work, Expand Claude's Reach

**Course Roadmap (5 Sections):**
1. **Meet Claude** — What is Claude? How do you talk to it? Great results?
2. **Organizing Your Work** — Projects, Artifacts, Skills: structure + reusable knowledge
3. **Expanding Reach** — Connectors, Enterprise Search, Research: web & tools
4. **Putting It Together** — Real-world use cases by role
5. **Conclusion** — Certificate, next steps

### Learning Objectives (Master-Level)
After this module, you should be able to:
- Explain Claude's **identity, principles, and design philosophy**
- Describe Claude's **core capabilities** versus a simple chatbot
- Write effective prompts using the **3-part framework**
- Identify all access modes: Web, Desktop, Mobile
- Use **Projects, Artifacts, and Skills** to organize work
- Set up **Connectors** and **Enterprise Search**
- Use **Research mode** for deep investigations
- Choose the right Claude tool for the right job

---

## Section 2 — Core Concepts: What Is Claude?

### 2.1 Claude's Identity and Design Principles

**What Claude Is:**
Claude is **NOT** just a chatbot. It is an AI assistant designed to be a **THINKING PARTNER** — a collaborator that can both **AUTOMATE** and **AUGMENT** your work.

**Core Design Principles (Constitutional AI):**

| Principle | Description |
|---|---|
| **Helpful** | Wide variety of tasks, reliable, consistent |
| **Harmless** | Avoids toxic, discriminatory, dangerous outputs |
| **Honest** | Operates transparently, avoids deception |

---

### Constitutional AI ⭐ (Key Certification Term)

**Definition:**
Constitutional AI is Anthropic's training approach in which Claude is trained to align with a set of human values and principles. Instead of just receiving human feedback, Claude is trained to **evaluate its own outputs** against these principles.

**Key Properties:**
- Avoids toxic or discriminatory outputs
- Refuses to help with illegal or unethical activities
- Behaves as a helpful, honest, and harmless AI system
- Operates transparently

> **⭐ Exam Tip:** *"Constitutional AI"* is the framework name. It comes from Anthropic. It's what makes Claude **STEERABLE** — you can guide its personality, tone, and behavior.

---

### What Makes Claude Different from a Simple Chatbot?

| Feature | Simple Chatbot | Claude |
|---|---|---|
| **Task Scope** | Q&A only | Writing, coding, analysis |
| **Reliability** | Varies | High predictability |
| **Context Window** | Short | 200K+ tokens (up to 1M) |
| **Steerability** | Limited | Highly steerable |
| **Memory** | None/basic | Memory + Projects |
| **File Handling** | None | PDF, DOCX, CSV, images |

---

### 2.2 Claude's Core Capabilities

#### 1. Writing & Content Creation
- Social media posts, professional emails, complex reports
- Takes direction on personality and tone
- Iterates with you on structure and clarity

#### 2. Research & Analysis
- Explores research angles, compiles findings, analyzes data
- Uploads documents and processes them
- **Context Window:** 200K+ tokens (~500 pages of text)
- **Extended:** Up to 1M tokens on Pro, Max, Team, Enterprise (Opus 4.7)

#### 3. Coding Assistance
- **Claude Opus 4.7** = "Best coding model in the world" (Anthropic claim)
- Writes, debugs, and explains code across multiple programming languages
- Strong performance on real-world coding tasks

#### 4. Problem-Solving & Reasoning
- Complex cognitive tasks, math, strategic thinking
- **Claude Opus 4.7** and **Sonnet 4.7** = **HYBRID MODELS**
- Two modes:
  - Near-instant responses (standard)
  - **Extended Thinking** (deep reasoning mode)

#### 5. Learning Support
- Adapts to your learning style and pace
- **LEARNING MODE:** Guides reasoning process, does NOT give direct answers
- Develops critical thinking skills (not dependency)

---

### Key Models to Know ⭐

| Model | Key Characteristics |
|---|---|
| **Claude Opus 4.7** | Most powerful. Best coder. Hybrid model. 1M token context window. Extended thinking. |
| **Claude Sonnet 4.7** | Hybrid model. Extended thinking available. Balanced speed and intelligence. |

---

### 2.3 Ways to Access Claude

> *Claude is the intelligence. The same intelligence runs across multiple interfaces.*

| Access Method | Best For / Key Features |
|---|---|
| **Claude.ai (Web/App)** | Conversations, writing, research, analysis. Primary interface. |
| **Claude Code** | Agentic coding tool. Edits files, runs commands, creates commits. Developer-focused. |
| **Claude + Slack** | Team communication. @Claude mentions. Searches workspace channels, DMs, files. |
| **Claude for Excel** | Sidebar in Microsoft Excel. Reads, analyzes, modifies Excel workbooks. |
| **Claude for PowerPoint** | Sidebar in Microsoft PowerPoint. Draft, edit, restructure presentations. |
| **Claude for Chrome** | Browser extension. Sidebar in Chrome. Observes and acts within browser. Research preview. Low-risk tasks only. |

**Plan Types:** Free Plan, Pro Plan, Max Plan, Team Plan, Enterprise Plan
> Conversations, projects, memory, and preferences **SYNC ACROSS ALL DEVICES**

---

## Section 3 — Your First Conversation (Prompting Framework)

### 3.1 The Prompting Mindset

**Key Principle:**
Talk to Claude like a **COWORKER** — naturally, concisely, conversationally.
- **NOT** like a search engine (one keyword query)
- **NOT** like a vending machine

> The REAL POWER of Claude = **Continued and frequent communication**, not one-off prompts.

---

### 3.2 The 3-Part Prompt Framework ⭐

Before your next conversation, consider:

| Element | Questions to Answer |
|---|---|
| **1. Setting the Stage** | What is your role? What are your objectives? What context about your work should Claude know? |
| **2. Defining the Task** | What action do you want Claude to take? Write? Analyze? Build? Research? Summarize? |
| **3. Specifying Rules** | What style or tone? What format? Any examples to attach? |

**Example Prompt (All 3 Elements):**
> *"I'm the marketing lead at an indie streaming startup, and we're preparing an investor pitch deck for Series A investors. Can you research the current state of the independent film streaming market and identify key trends, competitor positioning, and growth opportunities? Use current web research with citations and structure it as a professional report of up to 5 pages, with an executive summary, market analysis, competitive landscape, and growth opportunities."*

**Breakdown:**
- **Setting the Stage:** Marketing lead, indie streaming startup, Series A pitch
- **Defining the Task:** Research market, identify trends/competitors/opportunities
- **Specifying Rules:** Web research with citations, professional report, max 5 pages

> **⭐ Framework Origin (Exam-Important):** This 3-Part Framework is adapted from the **4D Framework for AI Fluency** — Developed by **Professor Rick Dakan** (Ringling College of Art and Design) and **Professor Joseph Feller** (University College Cork).

---

### 3.3 Adding Context

**Ways to Add Context:**
1. **File Uploads** — Claude analyzes both text AND visual elements
2. **Connectors** — Connect external tools (Google Drive, Slack, etc.)
3. **Custom Preferences** — Settings > General > 'What personal preferences?'

**Supported File Types:** PDF, DOCX, CSV, TXT, HTML, PNG, JPEG, and more

---

### 3.4 Iterating on Claude's Responses

| Approach | Example |
|---|---|
| **Ask Follow-Up Questions** | *"Can you expand on the second point?"* |
| **Provide Feedback** | *"This is good, but the tone is too formal. Can you make it more conversational?"* |
| **Redirect or Restart** | *"Actually, I was asking about X, not Y. Let me clarify..."* |

> **Pro Tip:** Click the **PENCIL ICON** on any message to **EDIT & RESUBMIT** your prompt.

---

### 3.5 Personalizing Claude

**Memory:**
- Automatically saves key context from conversations
- Saves: your role, preferences, past decisions, working style
- You don't repeat yourself every new chat
- Review, edit, or delete in Settings
- Syncs across all devices

**Styles:**
- Customize **HOW** Claude communicates
- Preset options: concise, formal, explanatory
- Or **CREATE your own custom style**
- Applies across ALL conversations automatically

---

## Section 4 — Common Challenges & AI Fluency

### 4.1 Common Challenges Troubleshooting Table

| Challenge | What's Happening | Fix |
|---|---|---|
| **Response too generic** | Not enough context in prompt | Add audience, role, constraints |
| **Response too long/short** | Claude guessing at length | Be explicit: *"Keep under 100 words"* |
| **Wrong format** | Understood what not how | Show don't tell. Give format example. |
| **Confident wrong info** | Hallucination | Verify facts. Ask Claude to cite sources. Enable web search. |
| **Wrong tone** | Defaults to helpful/professional | Describe tone: *"More conversational"* |

---

### 4.2 The Iteration Mindset

> **Key Principle:** Your **FIRST prompt** is NOT expected to produce perfect results. Think of it as the **START** of a conversation, not a one-shot request.

**Effective Claude Users:**
1. Treat first drafts as starting points
2. Give specific feedback (not just "make it shorter")
3. Know when to start fresh (new chat with clearer prompt)

---

### 4.3 AI Fluency & The 4D Framework

**AI Fluency Definition:**
> The ability to collaborate effectively with AI tools — not just clicking buttons, but developing the **JUDGMENT** to use AI well across different situations.

**The 4D Framework (Brief Recap):**
- **D1 — Delegation:** Decide what work belongs to you vs. AI vs. both
- **D2 — Description:** Communicate effectively with AI systems
- **D3 — Discernment:** Evaluate AI outputs thoughtfully and critically
- **D4 — Diligence:** Use AI responsibly, ethically, and transparently

**Relationship to Module 2:**
- The 3-Part Prompt Framework (Stage/Task/Rules) is rooted in **DESCRIPTION**
- Troubleshooting and evaluating Claude's outputs = **DISCERNMENT + DILIGENCE**

---

### 4.4 Evaluations (Evals) ⭐

**Definition:**
Evals (evaluations) are systematic ways to **TEST** how well Claude performs on specific types of tasks that **MATTER TO YOU**. Not complex infrastructure — just lightweight, structured testing.

**Why Evals Matter:**
- Understand where Claude adds most value in YOUR workflow
- Identify tasks needing more context or examples
- Build confidence in Claude's outputs for recurring tasks

**4-Step Simple Eval Approach:**

| Step | Action |
|---|---|
| **Step 1: Gather** | Collect 5-10 examples of a task you do regularly |
| **Step 2: Create Test Prompts** | Write prompts to generate similar outputs |
| **Step 3: Compare Outputs** | Does Claude capture key info? Is tone/style appropriate? What's missing? |
| **Step 4: Refine** | Adjust prompts, add examples, identify where human review is essential |

> **⭐ Exam Tip:** Evals = applying **DISCERNMENT** systematically to a specific workflow.

---

## Section 5 — Claude Desktop App: Chat, Cowork, Code

### 5.1 Overview of Three Modes

| Mode | Optimized For | Key Features | Engine |
|---|---|---|---|
| **CHAT** | Quick exchanges, exploring ideas, iterative drafting | Quick entry, dictation, screenshots, window sharing, desktop connectors | Standard Claude |
| **COWORK** | Complex/sustained work: research, analysis, docs | Folder access, scheduled tasks, subagents, dispatch, browser use, computer use, plugins, projects | Claude Code engine (local, independent work) |
| **CODE** | Building software: writing/testing/deploying code | Ask/Code/Plan modes, visual diffs, built-in terminal, git integration, local + remote environments | Claude Code engine (local or remote GitHub) |

> **Key Insight:** Cowork AND Code both run on the **SAME ENGINE** = Claude Code underneath. Both are capable of independent work, spinning up subagents, and sustaining long tasks.

---

### 5.2 Chat Mode (Deep Dive)

**What It Is:** Same as Claude.ai, with additional native desktop features.

**Unique Features (Desktop-Only):**
1. **QUICK ENTRY:** Double-tap Option key (Mac) = Claude overlay on top of any app
2. **SCREENSHOTS:** Capture screen or share window (faster than describing)
3. **DICTATION:** Talk through problems instead of typing (Mac)
4. **DESKTOP CONNECTORS:** Connect local tools and services

**Best For:**
- Quick questions while working on something else
- Brainstorming on the go
- Connecting notes from local apps (Apple Notes, etc.)
- Voicing ideas in between meetings

---

### 5.3 Cowork Mode (Deep Dive)

**What It Is:** Agentic tool for work that takes real effort. You give a goal, connect tools, and Claude does the work — pulling from many sources, producing finished deliverables.

**Cowork Workflow:**
1. You provide a goal and any constraints
2. Claude asks clarifying questions (scope, format, constraints)
3. Claude builds a **PLAN** (viewable in sidebar)
4. Claude executes — you see progress in real time (sources, files, plan stages)
5. Deliverable is produced

**Cowork Key Features:**

| Feature | Description |
|---|---|
| **Folder Access** | Point Claude to a folder; Claude reads, figures out relevance, saves work back |
| **Scheduled Tasks** | Claude handles recurring work on a SCHEDULE (daily briefings, weekly roundups) |
| **Subagents** | Background workers Claude spins up to handle task parts IN PARALLEL |
| **Dispatch** | Persistent conversation thread to continue Cowork FROM YOUR PHONE |
| **Projects (Cowork)** | Group related tasks into local workspaces with files, context, instructions, memory |
| **Browser Use** | Claude navigates websites, interacts with pages, pulls data |
| **Computer Use** | Claude navigates your computer directly (clicking, typing, opening apps) |
| **Plugins** | Give Claude capabilities it doesn't have natively (live financial data, internal KB) |
| **Protected Environment** | Cowork runs in a CONTAINED SPACE — Claude only accesses folders you explicitly share |

> **Computer Use Priority Order:** Connectors first → Chrome → Screen interaction
> **Availability:** Research Preview on Pro and Max plans (macOS only, Windows coming soon)

**Cowork Availability:** Pro, Max, Team, and Enterprise plans.

---

### 5.4 Code Mode (Deep Dive)

**What It Is:** Full development environment inside the desktop app. Claude works **DIRECTLY** in your codebase — reading, writing, modifying code, running commands.

**Local vs. Remote:**

| Type | Description |
|---|---|
| **LOCAL** | You select a folder on your computer. Claude accesses local tools, can run a dev server you preview in browser. |
| **REMOTE** | Connect a GitHub repository. Claude works in a CLOUD environment. Sessions continue even if you CLOSE THE APP. |

**Three Interaction Modes:**

| Mode | Description |
|---|---|
| **ASK** | Claude proposes every change, waits for your approval. You review visual diff and accept/reject before anything is modified. |
| **CODE** | Claude applies file changes automatically BUT checks before running terminal commands. |
| **PLAN** | Claude outlines full approach BEFORE touching anything. Dedicated plan viewer to review and revisit strategy. |

**Key Features:**
- Visual diffs (shows exactly what changed)
- Built-in terminal (shows commands as they run)
- Git integration (tracks every version, always can roll back)
- Multiple sessions across projects, filter by status/environment

**Code Availability:** Pro, Max, Team, and Enterprise plans.

---

### 5.5 Tools & Extensions by Mode (Comparison)

| Feature/Tool | Available In | Notes |
|---|---|---|
| Connectors | Chat, Cowork, Code | Local + remote in Cowork/Code |
| Skills | Chat, Cowork, Code | All modes |
| Claude in Chrome | Chat, Cowork, Code | All modes |
| **Plugins** | Cowork, Code | NOT in standard Chat |
| **Subagents** | Cowork | Cowork-specific |
| **Scheduled Tasks** | Cowork | Cowork-specific |
| **Computer Use** | Cowork | Research preview, Pro/Max |
| **Dispatch (Mobile)** | Cowork | Cowork-specific |
| **Visual Diffs** | Code | Code-specific |
| **Git Integration** | Code | Code-specific |
| **Hooks** | Code | Code-specific (not in Chat or Cowork) |
| **Quick Entry** | Chat | Chat-specific (Mac) |
| **Dictation** | Chat | Chat-specific (Mac) |

---

## Section 6 — Organizing Your Work: Projects

### 6.1 What Are Projects?

**Definition:**
Projects are **SELF-CONTAINED WORKSPACES** with their own:
- Memory
- Chat histories
- Knowledge bases
- Customized instructions

Think of them as dedicated environments for specific work streams.

**Key Benefit:**
No more re-uploading the same files every conversation. No more re-explaining context. Claude retains **full project knowledge** across all chats in the project.

**When to Use Projects (4 Scenarios):**
1. Reference materials you'll use **REPEATEDLY** (meeting notes, reports, data)
2. Consistent requirements for how Claude should respond
3. **Team collaboration** where multiple people share the same foundation
4. **Ongoing workflows** (not one-off questions)

---

### 6.2 Project Setup Step-by-Step

**Step 1 — Set Up Your Project**
- Hover over left sidebar > click "Projects"
- Or navigate to `claude.ai/projects`
- Click "+ New Project" (upper right)
- Give it a **DESCRIPTIVE NAME** (e.g., "Q4 Marketing Campaign")
- Add a brief description (for you and teammates — Claude doesn't see this)
- Choose visibility: **Private** OR **Share with organization**

**Step 2 — Add Project Instructions**
- Instructions tell Claude how to behave across ALL conversations in this project
- Click "Instructions" > open instructions panel

**Good Instructions Include:**
- Context about what you're working on
- Process instructions (*e.g., "First consider structure, then write the draft"*)
- Tone and style preferences
- Specific requirements (*e.g., "Always include a call-to-action"*)
- **Workflow Automation:** *"When I upload a meeting transcript, create a summary using this template"* — think of instructions as **PROGRAMMING CLAUDE'S BEHAVIOR**

Click "Save instructions" — applies to **EVERY** chat in this project.

**Step 3 — Build Your Knowledge Base**
- Files menu on right side of project's main page
- Click "+" to add content
- Supported: PDF, DOCX, CSV, TXT, HTML, and more
- Can connect to Google Drive directly

**What to Upload:**
- Reference documents (brand guidelines, style guides, templates)
- Background materials (research reports, meeting notes, requirements)
- Examples of work you want Claude to emulate
- Technical documentation or specifications

> **Pro Tip:** Name files **DESCRIPTIVELY**.
> - ✅ Good: `Q4-2024-Brand-Guidelines.pdf`
> - ❌ Bad: `document1.pdf`
> Claude uses file names to understand and retrieve the right information.

---

### 6.3 RAG Mode (Retrieval Augmented Generation) ⭐

**Definition:**
When your project knowledge base approaches the context window limit, Claude **SEAMLESSLY ENABLES RAG MODE**. Claude intelligently searches and retrieves only the **MOST RELEVANT** information for each question — instead of loading everything.

**Key Facts for Exam:**
- RAG expands project capacity by **UP TO 10X**
- Maintains response quality
- You see a **VISUAL INDICATOR** when RAG is enabled
- Experience feels the same to you — Claude still gives context-aware responses

> **Architect Analogy:**
> Regular mode = entire library open on your desk.
> RAG mode = librarian who fetches only the books relevant to your specific question.

---

### 6.4 Project Collaboration (Team & Enterprise)

**Permission Levels (3 Levels):**

| Level | Permissions |
|---|---|
| **Can View** | Read-only access + chat rights. Cannot make changes to project, instructions, or knowledge. |
| **Can Edit** | Full collaboration: modify instructions, update knowledge, manage members. Can actively contribute. |
| **Owner** | Controls everything, including who sees the project. Can share with specific people OR make visible to entire org. |

**How to Share:**
- Open project > click "Share project" button
- Add individual members by name/email
- Or copy-paste list of email addresses (bulk sharing)
- Or share with "Everyone at [your organization]" via Team tab

---

### 6.5 Example Projects (Real-World Use Cases)

- **Q4 Product Launch:** Upload product specs, competitive analysis, messaging notes
- **Research Support:** Competitive review, user research, customer feedback
- **Client Account Hub:** Client brand guidelines, past deliverables, communications
- **Event Planning Workspace:** Venue contracts, speaker bios, attendee data
- **Job Description Generator:** Past JDs, team charters, headcount request docs

---

### 6.6 Project Best Practices

1. **Start focused, then expand** (specific use case first)
2. **Keep knowledge base CURRENT** (outdated docs = outdated responses)
3. **Write CLEAR instructions** (vague = inconsistent results)
4. **Name documents descriptively**
5. **Reference documents by name** in your questions: *"Based on our Q3 report, what were the top customer concerns?"*

---

## Section 7 — Artifacts

### 7.1 What Are Artifacts?

**Definition:**
Artifacts are **STANDALONE, INTERACTIVE OUTPUTS** that Claude creates in a dedicated window alongside your conversation. They are rendered and ready to use — working websites, interactive charts, documents you can immediately download.

**When Does Claude Create an Artifact (Auto-criteria):**
- Content is **SIGNIFICANT and SELF-CONTAINED** (typically 15+ lines)
- Content you're likely to want to **EDIT, ITERATE ON, or REUSE**
- Represents **COMPLEX content** that stands alone without the conversation
- Content you'll want to **REFERENCE or USE LATER**

---

### 7.2 Artifact Types ⭐

| Artifact Type | Description / Best Use |
|---|---|
| **Documents** | Markdown, plain text, Word docs, PDFs, PowerPoint, Excel. Meeting notes, reports, project plans, blog posts. |
| **Code Snippets** | Working code in any language (Python, JS, C++, etc.). View, copy, or download. |
| **HTML Pages** | Complete web pages (HTML + CSS + JS in single file). Landing pages, forms, interactive demos, prototypes. |
| **SVG Images** | Scalable vector graphics: logos, icons, illustrations. Renders directly in artifact window. |
| **Mermaid Diagrams** | Flowcharts, sequence diagrams, Gantt charts, org charts. Describe relationships, Claude creates the diagram. |
| **React Components** | Interactive UI elements: calculators, dashboards, games. Not just mockups — actual logic, responds to user input. |

---

### 7.3 Creating and Using Artifacts

**Creating:**
- Just describe what you want — Claude determines if artifact is needed
- If Claude doesn't auto-create: *"Create this as an artifact"*
- Or: *"Show me this in an artifact"*

**Once Artifact Is Created (Right-side window):**
- **VIEW formats:** Toggle between preview and underlying code
- **COPY content:** Click copy icon
- **DOWNLOAD files:** Save as file to computer
- **VIEW CODE:** See exactly what Claude generated

**Sharing/Publishing:**

| Option | Description |
|---|---|
| **Copy or Download** | For personal use or sharing |
| **Share Within Org** | Team/Enterprise only — stays within org, requires team auth |
| **Publish Publicly** | Accessible to anyone with the link. Only SELECTED VERSION becomes public (chat stays private). Anyone can view and interact WITHOUT a Claude account. Others can **"REMIX"** it. **NOT** indexed by search engines. You can **UNPUBLISH** at any time. |

---

### 7.4 Artifact Tips for Best Results

1. **Be SPECIFIC:** *"Build a monthly budget tracker where I can input expenses by category, see a pie chart breakdown, and get a warning when I'm over budget"* (NOT just *"Build a budget tracker"*)
2. **Describe the END USER:** *"This flowchart is for new employees"* vs. *"This flowchart is for the engineering team"* = very different outputs
3. **ITERATE INCREMENTALLY:** Add one feature or change at a time
4. **REQUEST EXPLICITLY** if Claude responds in chat instead of artifact: *"Please create that as an artifact"*

---

## Section 8 — Skills

### 8.1 What Are Skills?

**Definition:**
Skills are **FOLDERS OF INSTRUCTIONS, SCRIPTS, and RESOURCES** that Claude loads **DYNAMICALLY** to improve performance on specialized tasks. They are **EXPERTISE PACKAGES** — they teach Claude how to complete specific tasks in a repeatable way.

**Real-World Analogy:**
When you've used Claude to create Excel, PowerPoint, Word, or PDF files, that's Skills running behind the scenes.

**Custom Skills Can Codify:**
- Quarterly variance analysis methodology
- Brand voice review process
- Compliance checklist execution
- Any repeatable organizational workflow

---

### 8.2 Two Types of Skills ⭐

| Type | Description |
|---|---|
| **Anthropic Skills** | Created and maintained by Anthropic. Include enhanced document creation for Excel, Word, PowerPoint, PDF. Available to ALL paid users. Claude invokes **AUTOMATICALLY** when relevant — no manual action needed. |
| **Custom Skills** | YOU or your organization create for specialized workflows. Examples: brand guidelines, meeting notes formatted in specific template, data analysis workflow. **PRIVATE** to your individual account. |

---

### 8.3 Enabling Skills

**Availability:** Feature preview for Pro, Max, Team, and Enterprise plans.

**Prerequisites:** Code execution and file creation must be **ENABLED**

**How to Enable:**
1. Navigate to Settings > Capabilities
2. Toggle on "Code execution and file creation"
3. Scroll to Skills section
4. Toggle individual skills on or off

> **Enterprise Plans:** Owners must first enable in Admin settings.
> **Team Plans:** Enabled by default at organization level.

---

### 8.4 Using Skills in Practice

**Skills Are Automatic** — Claude selects the right skill based on your request. You'll SEE it mentioned in Claude's chain of thought. Output = downloadable file (can save to computer or Google Drive directly).

**Example Prompts That Invoke Skills:**
- *"Create an Excel spreadsheet tracking monthly expenses with formulas for totals"*
- *"Turn this meeting notes document into a PowerPoint presentation"*
- *"Generate a PDF report summarizing this data"*
- *"Build a financial model in Excel with scenario analysis"*

**File Execution (Advanced):**
- Upload actual files (.xlsx, .pptx, .docx, .pdf)
- Claude creates an **UPDATED VERSION**
- Performs analyses, adds suggested edits, creates new slides
- Downloads or save to Drive when done
- Requires: "Allow limited network access" toggled on

---

### 8.5 Creating Custom Skills

**The Easiest Way:** Conversation with Claude itself. No code, no manual files.

**Step-by-Step Skill Creation:**
1. Start new chat: *"I want to create a skill for [workflow]"*
2. Answer Claude's questions:
   - What should this skill do?
   - What makes good output?
   - When would you use this skill?
3. Upload reference materials (templates, style guides, examples)
4. **SAVE YOUR SKILL:** Claude generates a properly structured file
5. **VIEW SKILLS:** Customize tab in left sidebar

**Managing Skills:**
- Find in Customize tab in left sidebar
- Edit manually OR by chatting with Claude
- Claude updates the files for you when you ask for changes

---

### 8.6 Skills vs. Projects (Key Comparison Table) ⭐

| Dimension | Projects | Skills |
|---|---|---|
| **PURPOSE** | Store KNOWLEDGE Claude references | Define PROCESSES Claude executes |
| **BEST FOR** | Long-term context, reference materials, team collaboration | Repeatable workflows, multi-step tasks, consistent methodology |
| **EXAMPLE** | Customer hub, research buddy, feedback generator | Brand guidelines, blog drafting, PDF creation, QBR template |
| **PERSISTENCE** | Knowledge across ALL chats in the project | Instructions applied when skill is invoked |
| **ANALOGY** | The **WHAT** (information) | The **HOW** (process/methodology) |

> **Key Insight:** Projects and Skills **COMPLEMENT EACH OTHER**.
> A "Customer Call Prep" SKILL can pull from customer profiles in a PROJECT.
> Project provides the **WHAT** (info). Skill provides the **HOW** (process).

---

### 8.7 Security Considerations for Skills

- Only install custom Skills from **TRUSTED SOURCES**
- Anthropic's built-in Skills are tested and maintained by Anthropic
- Custom Skills you upload are **PRIVATE** to your individual account
- If installing from external source: **REVIEW ITS CONTENTS** before use

---

## Section 9 — Connectors

### 9.1 What Are Connectors?

**Definition:**
Connectors transform Claude from an assistant into an **INFORMED COLLABORATOR** by giving Claude access to the same tools, data, and context you use every day. Instead of starting every conversation from scratch, Claude works with your **ACTUAL INFORMATION**.

**What Connectors Allow Claude to Do:**
- **READ** information (search files, retrieve documents)
- **PERFORM ACTIONS** (create content, update records, execute tasks)
- Work across connected applications — all from within your conversation

---

### 9.2 Model Context Protocol (MCP) ⭐

**Definition:**
MCP (Model Context Protocol) is the **OPEN STANDARD** that powers connectors.

> **⭐ Analogy (Exam-Important):**
> **"MCP is like USB-C for AI"**
> - Universal standard allowing Claude to connect to many different applications
> - Through a single, consistent interface
> - Developers can build connectors for any tool
> - Those connectors work seamlessly with Claude

---

### 9.3 Two Types of Connectors ⭐

| Type | Description | Examples |
|---|---|---|
| **Web Connectors** | Link Claude to CLOUD SERVICES | Google Drive, Notion, Slack, Asana, Gmail, Stripe, Salesforce |
| **Desktop Extensions** | Run LOCALLY on your computer through the Claude Desktop App. Give Claude access to local files and native applications. | Local file access, browser control, Figma integration |

---

### 9.4 Setting Up Connectors

**Web Connector Setup (5 Steps):**
1. **FIND:** Go to `claude.ai/directory`, or click + > Connectors in any chat
2. **CLICK:** Select "Connect" on the connector you want
3. **AUTHENTICATE:** Redirected to service's login page, sign in
4. **GRANT PERMISSIONS:** Review and authorize specific permissions
5. **TEST:** Return to Claude, try *"Can you access my [tool name]?"*

**Desktop Extension Setup (3 Steps):**
1. Download and install Claude Desktop app
2. Navigate to Settings > Extensions
3. Browse available extensions and click Install

---

### 9.5 Using Connectors in Practice

**Project Management (Asana, Linear, Jira):**
- *"What are my highest priority tasks due this week?"*
- *"Create a new task for reviewing the Q4 budget proposal"*

**Communication (Slack, Gmail):**
- *"Find the email thread where we discussed the vendor contract"*
- *"Draft a reply to the latest message in the #marketing channel"*

**Documentation (Notion, Google Drive, Confluence):**
- *"Search our documentation for our brand voice guidelines"*
- *"Summarize the meeting notes from last week's product review"*

**Business Tools (Stripe, Salesforce):**
- *"Show me revenue trends for the past quarter"*
- *"What's the status of the Acme Corp opportunity?"*

---

### 9.6 Security and Permissions

**Key Security Principles:**

| Principle | Description |
|---|---|
| **Scoped Access** | Permissions specific to what connector needs. Toggle individual permissions on/off. |
| **Claude Sees What You See** | Claude can only access data YOU have access to. Connecting work email ≠ access to CEO's inbox. |
| **Revocable At Any Time** | Disconnect via Claude's settings OR through third-party service's settings. |

> **Best Practice:** Only install connectors from TRUSTED SOURCES. Exercise same caution with custom connectors as with custom Skills.

---

## Section 10 — Enterprise Search

### 10.1 What Is Enterprise Search?

**Definition:**
Enterprise Search adds a dedicated **"Ask {Your Org Name}"** option to your sidebar. It's a **PRE-BUILT PROJECT** for your entire organization — company's knowledge base is already loaded.

**Key Distinction:**
Unlike regular chats with connectors, Enterprise Search is specifically designed for **INFORMATION GATHERING**, using **CUSTOM INSTRUCTIONS** configured by Anthropic.

> **Analogy:** Think of Enterprise Search as a *"Project for your whole company"* — your org's Slack, email, SharePoint, Google Drive all integrated from day one.

**Availability:** Team and Enterprise plans only. Must be enabled by workspace admin.

---

### 10.2 What Can You Ask Enterprise Search?

**Getting Up to Speed:**
- *"What happened yesterday while I was out?"*
- *"Summarize key updates across the business from the last week"*
- *"What are the current blockers on the Platform project?"*

**Policy and Process:**
- *"What is our company's remote work policy?"*
- *"How do I submit an expense report?"*

**Onboarding:**
- *"How does our authentication system work?"*
- *"Who should I talk to about learning the billing system?"*

---

### 10.3 How Enterprise Search Works

1. You ask a question
2. Claude searches across **ALL** connected tools (SharePoint, Slack, Gmail, Google Drive)
3. Synthesizes information into a **UNIFIED RESPONSE**
4. **ALWAYS CITES SOURCES** so you can get full context

**Setup — Two-Step Process:**

| Step | Responsible | Actions |
|---|---|---|
| **Step 1** | Admin/Owner | Click "Ask Your Org" in left sidebar → "Set up for your org" → Connect tools (Google Drive/SharePoint + Slack/Teams) → Customize project name → click "Finish set up" → becomes visible to ALL org members |
| **Step 2** | Users | Click "Ask {Org Name}" → follow onboarding flow → authenticate with each service → start asking questions |

**Security:**
- Enterprise Search **ONLY** shows what you already have permission to access
- Conversations remain **PRIVATE**
- Connected data is **NOT** indexed or stored separately

> **⭐ Key Exam Point:** More connectors = more comprehensive search results.

---

### 10.4 Research vs. Enterprise Search vs. Web Search ⭐

| Feature | Best For | Data Sources |
|---|---|---|
| **Web Search** | Quick specific facts, 1-2 sources | Public web |
| **Research Mode** | Comprehensive multi-source investigation | Web + connected integrations |
| **Enterprise Search** | Internal org knowledge | Org's Slack/docs/email |
| **Extended Thinking** | Deep reasoning, math, logic | No external data |

---

## Section 11 — Research Mode

### 11.1 What Is Research Mode?

**Definition:**
Research is an **ADVANCED FEATURE** that transforms Claude from a conversational assistant into a **SYSTEMATIC INVESTIGATOR**. Claude operates **AGENTICALLY** — conducting multiple searches that build on each other, determining what to investigate next, exploring different angles automatically.

> Think of it as a skilled research assistant who spends hours gathering information, cross-referencing sources, and compiling a comprehensive report — **but in MINUTES**.

**Time:** Most reports complete in **5-15 minutes**. Complex investigations up to **45 minutes**.

---

### 11.2 Key Research Features

- **AGENTIC OPERATION:** Claude autonomously decides what to search next
- **MULTI-STEP PROCESS:** Multiple searches build on each other
- **EXTENDED THINKING:** Automatically enabled with Research
- **CITATIONS:** Every claim links back to its source
- **CONNECTED INTEGRATIONS:** Pulls from your Gmail, Calendar, Drive alongside web

---

### 11.3 When to Use Research (vs. Other Features)

**Use Research When:**
- Comprehensive reports synthesizing from multiple sources
- In-depth analysis across web AND connected integrations (Google Workspace)
- Investigations typically requiring hours of manual work
- Comparative analysis (evaluating competitors, vendor options)
- Reports with citations you can verify

**Use Web Search Instead When:**
- You need a quick, specific fact (stock price, company address)
- Answer requires only 1-2 sources
- Speed matters more than comprehensiveness

**Use Extended Thinking Instead When:**
- Deep reasoning on complex problem NOT requiring external information
- Mathematical problems, code debugging, logical analysis
- Answer comes from REASONING through a problem, not gathering info

**Use Enterprise Search Instead When:**
- Answers draw from your ORG'S INTERNAL knowledge
- Question is specific to your company (policies, processes, past decisions)
- Not looking for public web information

---

### 11.4 How Research Works (4 Steps)

```
Step 1: PLANNING
    → Extended thinking automatically activates
    → Claude breaks down your request
    → Identifies what information it needs
    → Plans how to investigate different angles

Step 2: MULTIPLE SEARCHES
    → Conducts MANY searches that build on each other
    → Determines next search based on what it found
    → Pursues promising leads, fills gaps
    → No step-by-step direction from you needed

Step 3: SYNTHESIS
    → Gathers from web + any connected integrations (Gmail, Calendar, Drive)
    → Compiles into comprehensive, well-organized report

Step 4: CITATIONS
    → Every claim links back to its source
    → Easy to verify and dig deeper
```

---

### 11.5 Enabling Research

**How to Enable:**
1. Click the `+` button (bottom left of chat interface)
2. Select "Research" from the menu (highlighted when active)
3. Enter your prompt and submit
4. Claude works in background with progress indicators

> **IMPORTANT:** WEB SEARCH MUST BE ENABLED for Research to function.

> **Pro Tip:** You can **TURN OFF WEB SEARCH** to do **INTERNAL-ONLY** research across connected tools — great for *"What did our team discuss about the Q3 launch across Slack and Docs?"*

---

### 11.6 Tips for Effective Research Prompts

Since Research takes 5-45 minutes, invest time crafting your prompt:

1. **Be SPECIFIC about goals:**
   - ❌ *"Tell me about the EV market"*
   - ✅ *"Analyze the electric vehicle battery market — identify key players, technology trends, and supply chain challenges for investment decisions"*

2. **Specify sections/structure:**
   - *"Compare venue options for team offsite including: location & accessibility, meeting space, catering options, and pricing considerations"*

3. **Include relevant constraints:**
   - Budget ranges, timelines, geographic requirements help Claude focus

4. **Ask Claude to help refine your prompt:**
   - *"Help me write a better Research prompt for this question before I enable the Research feature"*

---

## Section 12 — Real-World Use Cases by Role

| Role | Use Cases |
|---|---|
| **General Professional** | Generate project status reports · Analyze patterns in user feedback · Package brand guidelines in a Skill |
| **Sales** | Build battle card library · Prepare for sales deals (research prospects) · Create sales reports |
| **Marketing** | Analyze campaign performance · Adapt content across platforms |
| **Finance** | Build financial models · Draft investment memos · Understand inherited spreadsheets |
| **HR** | Create new hire onboarding guides (tailored to different roles) |
| **Legal** | Track discovery timelines · Analyze patterns in legal documents |
| **Research** | Plan literature reviews · Verify statistics from raw data |

---

## Section 13 — Other Claude Tools (Specialized Interfaces)

### Complete Tool Summary Table

| Tool | Best For | Where It Runs |
|---|---|---|
| **Claude.ai** | General tasks, research, writing, analysis, file creation | Web, desktop, mobile |
| **Claude Code** | Software development, codebase navigation, git workflows | Terminal/IDE/browser |
| **Claude Cowork** | Complex multi-step tasks: research briefs, document creation, file org, data analysis | Desktop (+ mobile via Dispatch) |
| **Claude in Slack** | Team collaboration, meeting prep, quick answers in context | Slack workspace |
| **Claude for Excel** | Spreadsheet analysis, financial modeling, formula debugging | Microsoft Excel sidebar |
| **Claude for PowerPoint** | Slide creation, presentation editing, formatting and design | Microsoft PowerPoint sidebar |
| **Claude for Chrome** | Web research, email management, browser automation | Chrome browser sidebar (research preview) |

---

### Claude Code (Detailed)
- Agentic coding tool: terminal, IDE, browser, or Slack
- Understands codebase, executes commands, handles dev workflows
- Write features in plain English → Claude writes code, runs tests, creates commits
- Debug by pasting error messages → Claude analyzes codebase, identifies + fixes
- Navigate unfamiliar codebases: ask how parts work together
- Automate tedious tasks: lint errors, merge conflicts, release notes
- Works in **YOUR TERMINAL** alongside existing IDE (no separate interface needed)

### Claude in Slack (Detailed)
- Integrates directly into Slack
- Draft responses, summarize threads, break down complex discussions
- Prepare for meetings: pull together relevant conversations and files
- Onboarding help: review channel history for ongoing projects
- Tag @Claude in a thread → can spin up Claude Code session using context

### Claude for Chrome (Important Notes)
- Currently in **RESEARCH PREVIEW**
- Anthropic recommends **LOW-RISK TASKS** on **TRUSTED WEBSITES** only
- Asks permission before **HIGH-RISK ACTIONS** (purchasing, sharing personal data)
- **BLOCKED** categories: financial services sites, adult content (by default)
- Great for niche internal tools, CRMs, dashboards that maintain context across tabs

---

## Section 14 — Key Certification Topics ⭐

> ★ STARRED = Most likely to appear on certification exam

| Topic | Key Points |
|---|---|
| ★ **Constitutional AI** | Claude's design philosophy: Helpful, Harmless, Honest. AI trained to align with human values. |
| ★ **Claude Models** | Opus 4.7: Most powerful, best coding model, 1M token context, hybrid model. Sonnet 4.7: Hybrid model, extended thinking available. HYBRID MODEL = two modes: near-instant + extended thinking. |
| ★ **Context Window** | Standard: 200K+ tokens (~500 pages). Extended: Up to 1M tokens (Opus 4.7, Pro/Max/Team/Enterprise). |
| ★ **Extended Thinking** | Claude works through problems step-by-step. Automatically enabled with Research mode. |
| ★ **3-Part Prompt Framework** | Setting the Stage (role, objectives, context). Defining the Task (what action). Specifying Rules (style, tone, format, examples). |
| ★ **4D AI Fluency Framework** | Delegation, Description, Discernment, Diligence. Origin: Professor Rick Dakan & Professor Joseph Feller. |
| ★ **Evals** | Systematic evaluation of Claude's output quality for your specific workflows. 4-step process: Gather → Create prompts → Compare → Refine. |
| ★ **Chat / Cowork / Code Modes** | Three desktop app modes and what each is for. Cowork and Code share the Claude Code engine. |
| ★ **Projects** | Self-contained workspaces: memory, chat history, knowledge base, instructions. When to use, how to set up, RAG mode. |
| ★ **RAG Mode** | Retrieval Augmented Generation. Auto-triggered when knowledge base approaches context window limit. Expands capacity UP TO 10X. |
| ★ **Artifacts** | 6 types: Documents, Code, HTML pages, SVG images, Mermaid diagrams, React. Publish/Share/Remix capability. |
| ★ **Skills** | Two types: Anthropic Skills vs. Custom Skills. Skills = how to do tasks (process) vs. Projects = what to know (knowledge). |
| ★ **Connectors & MCP** | MCP = "USB-C for AI" — universal standard for tool connection. Two connector types: Web Connectors vs. Desktop Extensions. |
| ★ **Enterprise Search** | "Pre-built Project for your whole org". Team and Enterprise plans only. Two-step setup: Admin first, then user auth. |
| ★ **Research Mode** | Agentic, multi-source, multi-step investigation. 5-45 minutes, extended thinking auto-enabled, cites sources. Web search MUST be enabled. |
| ★ **Scheduled Tasks** | Recurring automated tasks in Cowork mode. |
| ★ **Subagents** | Parallel background workers for complex multi-part tasks (Cowork). |
| ★ **Computer Use** | Research preview, Pro/Max, macOS only (Windows coming soon). |
| ★ **Dispatch** | Continue Cowork conversations from mobile phone. |
| ★ **Projects vs. Skills** | Projects = Knowledge (what). Skills = Process (how). They complement each other. |

---

## Section 15 — Interview Questions & Answers

**Q1: What is Claude and how is it different from a chatbot?**

> Claude is an AI assistant designed to be a **thinking partner**, not just a question-answering tool. Unlike simple chatbots, Claude handles a wide variety of tasks (writing, coding, analysis, research), maintains high reliability and predictability, has a large context window (200K+ tokens, up to 1M), is guided by **Constitutional AI** principles (Helpful, Harmless, Honest), is highly steerable, has persistent memory, and supports file uploads, connectors, projects, artifacts, and skills.

---

**Q2: What is Constitutional AI?**

> Constitutional AI is Anthropic's training methodology for Claude. Rather than relying solely on human feedback, Claude is trained to evaluate its own outputs against a "constitution" of human values and principles. This ensures Claude avoids toxic/discriminatory outputs, refuses illegal/unethical requests, and operates transparently. It's what makes Claude reliably **helpful, harmless, and honest**.

---

**Q3: What are the three elements of an effective prompt?**

> The 3-Part Prompt Framework:
> 1. **Setting the Stage:** Your role, objectives, and relevant context
> 2. **Defining the Task:** The specific action you want Claude to take
> 3. **Specifying Rules:** Style, tone, format requirements, and any examples
>
> This framework is adapted from the 4D AI Fluency Framework by Professors **Rick Dakan** and **Joseph Feller**.

---

**Q4: What is the difference between Chat, Cowork, and Code in the Claude Desktop App?**

> - **CHAT:** For quick exchanges, brainstorming, iterative drafting. Works like Claude.ai with added desktop features (quick entry, screenshots, dictation, connectors).
> - **COWORK:** For complex, sustained work requiring research, multi-source synthesis, and finished deliverables. Features: folder access, scheduled tasks, subagents, browser use, computer use, and plugins. Runs on the Claude Code engine.
> - **CODE:** For building software. Claude works directly in your codebase with full access to files, terminal, and dev tools. Supports local and remote (GitHub) environments, three interaction modes (Ask/Code/Plan), visual diffs, and git integration.
>
> **Cowork and Code share the same Claude Code engine.**

---

**Q5: What are Projects and when should you use them?**

> Projects are self-contained workspaces with their own memory, chat histories, knowledge bases, and customized instructions. Use Projects when you have:
> - Reference materials you'll repeatedly use
> - Consistent requirements for how Claude should respond
> - Team collaboration needs
> - Ongoing workflows (not one-off questions)
>
> Projects scale automatically via **RAG** (Retrieval Augmented Generation) when the knowledge base approaches the context window limit, expanding capacity by up to **10x**.

---

**Q6: How do Skills differ from Projects?**

> - **Projects** store **KNOWLEDGE** that Claude references (the "what")
> - **Skills** define **PROCESSES** that Claude executes (the "how")
>
> Projects are knowledge hubs with reference materials. Skills are procedural machines encoding step-by-step methodology. They **complement each other**: a "Customer Call Prep" skill can pull information from customer profiles stored in a Project's knowledge base.

---

**Q7: What is the Model Context Protocol (MCP) and why does it matter?**

> MCP (Model Context Protocol) is an **open standard** that powers Claude's connectors — often called **"USB-C for AI."** It provides a universal, consistent interface for Claude to connect to many different applications. Because MCP is an open standard, any developer can build a connector for any tool, and those connectors work seamlessly with Claude. This enables Claude to access your real-time data in Google Drive, Slack, Asana, Salesforce, and hundreds of other tools.

---

**Q8: What is Enterprise Search and how is it different from regular Connectors?**

> Enterprise Search adds a dedicated "Ask {Org Name}" feature to the sidebar. Think of it as a **pre-built Project for your entire organization** — company knowledge is pre-loaded. Unlike regular chats with connectors enabled, Enterprise Search is specifically designed for **information gathering**, using custom instructions configured by the Anthropic team. It searches across all your org's connected tools (SharePoint, Slack, Gmail, Google Drive) and synthesizes information with source citations. Available on **Team and Enterprise plans** only.

---

**Q9: What is Research Mode and when should you use it?**

> Research mode transforms Claude into a **systematic investigator** that operates agentically — conducting multiple searches that build on each other, determining what to investigate next, and synthesizing findings into a comprehensive report with citations. **Extended thinking is automatically enabled** with Research.
>
> Use Research when you need: comprehensive multi-source reports, in-depth analysis from web + connected integrations, comparative analysis, or tasks that would typically take hours of manual research. Reports complete in **5-45 minutes**. **Web search must be enabled** for Research to work.

---

**Q10: What are Artifacts and what types exist?**

> Artifacts are standalone, interactive outputs Claude creates in a dedicated window alongside the conversation — rendered and ready to use, not buried in chat.
>
> **Six types:**
> 1. Documents (Markdown, Word, PDF, PowerPoint, Excel)
> 2. Code Snippets (any programming language)
> 3. HTML Pages (complete web pages with HTML/CSS/JS)
> 4. SVG Images (logos, icons, illustrations)
> 5. Mermaid Diagrams (flowcharts, Gantt charts, org charts)
> 6. React Components (interactive UI with actual logic)
>
> Artifacts can be published publicly (anyone with link can view/remix) or shared within your organization (Team/Enterprise plans).

---

**Q11: What is RAG and how does it work in Claude Projects?**

> **RAG (Retrieval Augmented Generation)** is automatically enabled when a Project's knowledge base approaches the context window limit. Instead of loading ALL project content into memory at once, Claude intelligently searches and retrieves only the **MOST RELEVANT** information needed to answer each question. RAG expands project capacity by **up to 10x** while maintaining response quality. Users see a visual indicator when RAG is active.

---

**Q12: What is Computer Use in Claude Cowork and what are its limitations?**

> Computer Use is a Cowork feature that allows Claude to navigate your computer directly — clicking, typing, and opening apps just like a human user. It's used when no connector or plugin exists for what Claude needs to do. Claude follows a priority order: Connectors first → Chrome → Screen interaction. You receive a permission prompt before Claude accesses each app, and you can set up a blocklist for restricted apps. Currently in **research preview**, available on **Pro and Max plans**, **macOS only** (Windows coming soon).

---

**Q13: What is Extended Thinking and which models support it?**

> Extended thinking is a mode where Claude works through complex problems step-by-step before giving a final answer, enabling deeper reasoning for difficult tasks (math, strategic analysis, complex problem-solving). **Claude Opus 4.7** and **Claude Sonnet 4.7** are **hybrid models** that support extended thinking. Extended thinking is also automatically enabled when Research mode is activated.

---

**Q14: How does Memory work in Claude?**

> Claude's **Memory** feature automatically saves key context from conversations — your role, preferences, past decisions, and working style — so you don't have to repeat yourself every time you start a new chat. Memory syncs across all your devices. You can review, edit, or delete anything Claude remembers in Settings at any time. **Memory is separate from Projects:** memory is global across all conversations, while Projects have their own scoped context.

---

**Q15: What security controls exist for Claude Connectors?**

> Three key security principles:
> 1. **SCOPED ACCESS:** Permissions are specific to what each connector needs; you can toggle individual permissions on/off
> 2. **CLAUDE SEES WHAT YOU SEE:** Claude can only access data you personally have access to in the connected service
> 3. **REVOCABLE AT ANY TIME:** Disconnect via Claude's Settings or the third-party service's own security settings
>
> Enterprise Search ensures conversations remain private and connected data is not separately indexed or stored.

---

## Section 16 — Certification Questions

**Q1:** Which Anthropic principle describes training Claude to align with human values through a set of built-in principles, making it helpful, harmless, and honest?
- A) Reinforcement Learning from Human Feedback (RLHF)
- B) **Constitutional AI** ✅
- C) Fine-Tuning Alignment Protocol
- D) Retrieval Augmented Generation

> **Explanation:** Constitutional AI is Anthropic's approach to training Claude to evaluate its own outputs against a defined set of principles. RLHF is a different technique. RAG is a knowledge retrieval method, not a training philosophy.

---

**Q2:** A user enables Research mode in Claude to investigate competitor pricing. Which feature is automatically activated alongside Research?
- A) Subagents
- B) **Extended Thinking** ✅
- C) Computer Use
- D) Enterprise Search

> **Explanation:** When Research is enabled, Extended Thinking automatically activates.

---

**Q3:** What is the standard context window available for most Claude plans?
- A) 50K tokens
- B) 100K tokens
- C) **200K+ tokens** ✅
- D) 1M tokens

> **Explanation:** Claude's standard context window is 200K+ tokens (~500 pages of text). The 1M token extended context window is available with Opus 4.7 on Pro, Max, Team, and Enterprise plans.

---

**Q4:** Which of the following BEST describes the Model Context Protocol (MCP)?
- A) Claude's internal reasoning mechanism for complex tasks
- B) A training protocol for fine-tuning Claude on custom data
- C) **An open standard (like USB-C) for connecting Claude to external tools** ✅
- D) A security framework protecting sensitive enterprise data

---

**Q5:** You want Claude to execute a recurring morning task automatically — pulling a Slack digest and your calendar agenda. Which Claude Desktop feature enables this?
- A) Dispatch
- B) Subagents
- C) **Scheduled Tasks** ✅
- D) Computer Use

> **Explanation:** Scheduled Tasks in Cowork allow you to define recurring automated work on a schedule. Claude catches up if the computer was closed when the task was due.

---

**Q6:** What happens when a Project's knowledge base approaches the context window limit?
- A) Claude stops accepting new uploads
- B) The project must be manually reset
- C) **RAG mode is automatically enabled, expanding capacity up to 10x** ✅
- D) Older documents are automatically deleted

---

**Q7:** What permission level in a shared Project allows a member to modify instructions and manage other members?
- A) Can View
- B) **Can Edit** ✅
- C) Contributor
- D) Owner

> **Explanation:** "Can Edit" members have full collaboration power including modifying instructions, updating knowledge, and managing other members. "Can View" is read-only. "Owner" controls everything including sharing visibility.

---

**Q8:** Claude for Chrome is currently available as what type of release?
- A) General Availability (GA)
- B) Beta Release
- C) **Research Preview** ✅
- D) Enterprise Preview

---

**Q9:** Which Claude Desktop mode allows you to hand off a task from your phone to Claude running on your desktop computer?
- A) Cowork's Subagents
- B) **Cowork's Dispatch** ✅
- C) Code's Remote Environment
- D) Chat's Quick Entry

> **Explanation:** Dispatch provides a persistent conversation thread allowing you to continue Cowork conversations from the Claude mobile app. Requires both desktop and mobile apps, with the computer awake and desktop app open.

---

**Q10:** Which of the following is NOT one of the six types of Artifacts Claude can create?
- A) Mermaid Diagrams
- B) React Components
- C) **Database Schemas** ✅
- D) SVG Images

> **Explanation:** The six artifact types are: Documents, Code Snippets, HTML Pages, SVG Images, Mermaid Diagrams, and React Components. Database Schemas are not a named artifact type (though Claude can write SQL/schema code within a code snippet artifact).

---

**Q11:** When a user publishes an artifact publicly, which statement is TRUE?
- A) The entire conversation is made public along with the artifact
- B) The published artifact is indexed by search engines like Google
- C) **Only the selected version becomes public; the chat remains private** ✅
- D) Viewers must have a Claude account to see published artifacts

---

**Q12:** In Code mode, which interaction setting requires Claude to propose EVERY change and wait for your approval before modifying anything?
- A) Plan Mode
- B) **Ask Mode** ✅
- C) Review Mode
- D) Code Mode

> **Explanation:** In Ask mode, Claude proposes every change and waits for your approval. Code mode applies file changes automatically (but checks before running terminal commands). Plan mode outlines the full approach without touching anything first.

---

**Q13:** What is the PRIMARY difference between Enterprise Search and using regular Connectors in Claude?
- A) Enterprise Search can only access public web sources
- B) **Enterprise Search is purpose-built for internal org knowledge gathering, with custom Anthropic-configured instructions** ✅
- C) Enterprise Search is available on all plan types
- D) Regular connectors are only available in Code mode

---

**Q14:** A user wants to encode their company's brand review process so Claude automatically follows the same steps every time. Which feature is MOST appropriate?
- A) Memory
- B) Project Instructions
- C) **Custom Skills** ✅
- D) Artifacts

> **Explanation:** Custom Skills are expertise packages that teach Claude how to complete SPECIFIC TASKS in a REPEATABLE WAY — encoding multi-step processes and methodologies. Project Instructions tell Claude how to behave in a project's conversations (not a repeatable procedural workflow).

---

**Q15:** According to the course, which of the following BEST describes the relationship between Projects and Skills?
- A) They are redundant — both provide the same functionality
- B) Projects are for individual use; Skills are for team collaboration
- C) **Projects store knowledge (the "what"); Skills define processes (the "how")** ✅
- D) Skills replace Projects in the Enterprise plan

---

## Section 17 — Revision Notes (By Level)

### Beginner Level
- Claude = **AI thinking partner**, not just chatbot
- **Constitutional AI** = Helpful, Harmless, Honest (Anthropic's design principle)
- Talk to Claude like a **coworker** — naturally and conversationally
- Use **3-part prompts:** Setting the Stage + Defining the Task + Specifying Rules
- Your first prompt is **NOT expected to be perfect** — iterate
- Upload files (PDF, DOCX, CSV, images) to give Claude context
- **Memory** saves your preferences across conversations

### Intermediate Level
- **Projects** = self-contained workspaces with knowledge bases and instructions
- **RAG** auto-enables when knowledge base exceeds context window (10x expansion)
- **Artifacts** = standalone, interactive outputs (6 types)
- **Skills** = expertise packages for repeatable workflows (Anthropic vs. Custom)
- Projects store knowledge; Skills define process — they **complement each other**
- **Evals** = structured testing of Claude's performance on YOUR specific workflows
- Connectors powered by **MCP** ("USB-C for AI") for real-time tool integration

### Advanced Level
- **Chat / Cowork / Code** = 3 desktop modes sharing the Claude Code engine (Cowork/Code)
- Cowork: **Subagents** for parallel work, **Scheduled Tasks**, **Dispatch** (mobile), **Computer Use**
- Code: Ask/Code/Plan interaction modes, local vs. remote GitHub environments
- **Enterprise Search** = org-wide pre-built Project (Team/Enterprise only)
- **Research mode** = agentic multi-step investigation with auto Extended Thinking
- Model comparison: **Opus 4.7** (most powerful, 1M tokens) vs. **Sonnet 4.7** (balanced)
- Security controls: Scoped access, you-only visibility, revocable at any time
- Custom Skills created through conversation with Claude (no manual coding needed)

---

## Section 18 — One-Page Quick Revision Summary

**Claude's Identity:**
> Constitutional AI | Helpful + Harmless + Honest | Thinking partner not chatbot

**Models:**
> Opus 4.7 = Best coder, most powerful, 1M tokens, hybrid model
> Sonnet 4.7 = Hybrid model, balanced, extended thinking available

**Access Methods:**
> Claude.ai | Claude Code | Slack | Excel | PowerPoint | Chrome (research preview)

**Prompting (3 Parts):**
> Stage (who/what/why) + Task (what action) + Rules (style/format/examples)

**Iterating:** First prompt is starting point. Iterate with follow-ups/edits/restarts.

**Personalizing:** Memory (saves your profile) + Styles (how Claude communicates)

**Desktop App Modes:**
- **CHAT** = Quick exchanges + desktop extras (quick entry, dictation, screenshots)
- **COWORK** = Complex sustained work (subagents, scheduled tasks, dispatch, computer use)
- **CODE** = Software development (Ask/Code/Plan, visual diffs, git, local/remote)

**Projects (Know the 4):**
> Purpose: Self-contained workspaces | RAG: 10x capacity | Setup: 3 steps
> Sharing: View / Edit / Owner (Team + Enterprise only)

**Artifacts (Know the 6):**
> Documents | Code | HTML Pages | SVG Images | Mermaid Diagrams | React Components
> Publish = Public link, no account needed, remixable, NOT indexed by search

**Skills (Know the difference):**
> Anthropic Skills = auto-invoked, built-in doc creation
> Custom Skills = YOUR workflow, created via conversation, private to your account
> Projects = Knowledge (what) | Skills = Process (how) | They complement each other

**Connectors:**
> MCP = "USB-C for AI" | Web Connectors (cloud) | Desktop Extensions (local)
> Security: Scoped → You-only-see → Revocable

**Enterprise Search:**
> "Pre-built Project for your org" | Team + Enterprise only | Admin setup first
> Searches: SharePoint + Slack + Gmail + Drive | Cites sources | Private conversations

**Research Mode:**
> Agentic + multi-step + cites sources | 5-45 min | Extended Thinking auto-enabled
> Web Search MUST be on | Use internal-only by turning off web search

**Evals:**
> Gather (5-10 examples) → Create prompts → Compare outputs → Refine approach

---

## Section 19 — Certification Preparation Roadmap

| Phase | Timeline | Focus Areas |
|---|---|---|
| **Phase 1 — Foundation** | Week 1 | Claude's identity, principles, Constitutional AI. 3-Part Prompt Framework. All Claude access methods. Practice iterative conversations. 4D Framework connection. |
| **Phase 2 — Organizing Work** | Week 2 | Projects: when to use, 3-step setup, RAG mode, collaboration permissions. Artifacts: all 6 types, sharing/publishing rules, remix capability. Skills: two types, enabling, creating via conversation. Projects vs. Skills distinction. |
| **Phase 3 — Expanding Reach** | Week 3 | Connectors: MCP definition, web vs. desktop extensions, security controls. Enterprise Search: what it is, admin setup vs. user setup, security. Research Mode: 4-step workflow, when to use. Know the decision table. |
| **Phase 4 — Desktop App Mastery** | Week 4 | Chat mode: quick entry, dictation, screenshots, connectors. Cowork mode: subagents, scheduled tasks, dispatch, browser use, computer use. Code mode: Ask/Code/Plan, local vs. remote, visual diffs, git. Plan requirements. |
| **Phase 5 — Exam Readiness** | Week 5 | Complete all 15 certification questions without notes. Practice all 15 interview questions. Do one-page summary quiz from memory. Review "most important topics" list. |

---

## Section 20 — Top 20 Key Takeaways

1. Claude is an **AI thinking partner** — built to augment AND automate work.
2. **Constitutional AI** is Anthropic's design framework: **Helpful, Harmless, Honest**.
3. Claude **Opus 4.7** = most powerful model, best coder, 1M token context, hybrid.
4. **Hybrid models** (Opus/Sonnet 4.7) offer two modes: instant + extended thinking.
5. The **3-Part Prompt Framework:** Setting the Stage + Task + Rules. Adapted from the 4D Framework by Professors **Rick Dakan** and **Joseph Feller**.
6. **Iteration is normal and productive.** First prompts are starting points, not finished products.
7. **Memory** saves your profile globally. **Projects** save context scoped to a workspace.
8. **Chat / Cowork / Code:** three desktop modes. Cowork and Code share the Claude Code engine and can run subagents, sustain long tasks, work independently.
9. **Projects** = self-contained workspaces. **RAG mode** auto-enables for **10x capacity**.
10. **Artifacts** = standalone interactive outputs. **6 types.** Publishable, remixable.
11. **Skills** = expertise packages for repeatable workflows. Two types: **Anthropic** (auto-invoked) and **Custom** (created via conversation, private to you).
12. **Projects (knowledge/what)** + **Skills (process/how)** = most powerful combination.
13. **MCP** = Model Context Protocol = **"USB-C for AI."** Powers all connectors.
14. Two connector types: **Web** (cloud services) and **Desktop Extensions** (local tools).
15. **Enterprise Search** = pre-built org-wide Project. **Team/Enterprise only.** Admin sets up first; users authenticate separately. Cites sources.
16. **Research mode** = agentic multi-step investigation with auto Extended Thinking. **5-45 minutes.** Web Search must be on. Cites all sources.
17. Know the **Research vs. Web Search vs. Enterprise Search vs. Extended Thinking** decision framework — it's a common exam scenario question.
18. Cowork advanced features: **Subagents** (parallel workers), **Scheduled Tasks** (recurring automation), **Dispatch** (mobile handoff), **Computer Use** (screen control).
19. Code mode: **Ask** (approve every change) / **Code** (auto file edits) / **Plan** (review before starting). Local or **Remote** (GitHub cloud) environments.
20. Security principles for connectors: **Scoped access** + **You-only visibility** + **Revocable anytime.** Never share sensitive/PII data in AI prompts.

---

> **END OF MODULE 2 — CLAUDE 101: COMPLETE PREPARATION NOTES**
> *Covers: What is Claude | Prompting | Desktop Modes | Projects | Artifacts | Skills | Connectors | Enterprise Search | Research | Use Cases | Tools*
>
> *Next Module: Claude_Module3_Mcp.md*
