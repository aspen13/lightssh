<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pages/common/util/taglibs.jsp" %>

	<ul id="mytree" style="width:200px;clear: none;float:left;height: 100%;">
		<li><input type="checkbox" /><a href="?1">Item 1</a>
			<ul>
				<li><input type="checkbox" /><a href="?1.0">Item 1.0 </a>

					<ul>
						<li><input type="checkbox" /><a href="?1.0.0">Item 1.0.0</a></li>
					</ul>
				</li>
				<li><input type="checkbox" /><a href="?1.1">Item 1.1</a></li>
				<li><input type="checkbox" /><a href="?1.2">Item 1.2</a>
					<ul>

						<li><a href="?1.2.0">Item 1.2.0</a>
						<ul>
							<li><a href="?1.2.0.0">Item 1.2.0.0</a></li>
							<li><a href="?1.2.0.1">Item 1.2.0.1</a></li>
							<li><a href="?1.2.0.2">Item 1.2.0.2</a></li>
						</ul>
					</li>

						<li><a href="?1.2.1">Item 1.2.1</a>
						<ul>
							<li><a href="?1.2.1.0">Item 1.2.1.0</a></li>
						</ul>
					</li>
						<li><a href="?1.2.2">Item 1.2.2</a>
						<ul>

							<li><a href="?1.2.2.0">Item 1.2.2.0</a></li>
							<li><a href="?1.2.2.1">Item 1.2.2.1</a></li>
							<li><a href="?1.2.2.2">Item 1.2.2.2</a></li>
						</ul>
					</li>
					</ul>
				</li>

			</ul>
		</li>
		<li><a href="?2">Item 2</a>
			<ul>
				<li><span>Item 2.0</span>
					<ul>
						<li><a href="?2.0.0">Item 2.0.0</a>

						<ul>
							<li><a href="?2.0.0.0">Item 2.0.0.0</a></li>
							<li><a href="?2.0.0.1">Item 2.0.0.1</a></li>
						</ul>
					</li>
					</ul>
				</li>
				<li><a href="?2.1">Item 2.1</a>

					<ul>
						<li><a href="?2.1.0">Item 2.1.0</a>
						<ul>
							<li><a href="?2.1.0.0">Item 2.1.0.0</a></li>
						</ul>
					</li>
						<li><a href="?2.1.1">Item 2.1.1</a>

						<ul>
							<li><a href="?2.1.1.0abc">Item 2.1.1.0</a></li>
							<li><a href="?2.1.1.1">Item 2.1.1.1</a></li>
							<li><a href="?2.1.1.2">Item 2.1.1.2</a></li>
						</ul>
					</li>
						<li><a href="?2.1.2">Item 2.1.2</a>

						<ul>
							<li><a href="?2.1.2.0">Item 2.1.2.0</a></li>
							<li><a href="?2.1.2.1">Item 2.1.2.1</a></li>
							<li><a href="?2.1.2.2">Item 2.1.2.2</a></li>
						</ul>
					</li>
					</ul>

				</li>
			</ul>
		</li>
		<li><a href="?3">Item 3</a>
			<ul>
				<li class="open"><a href="?3.0">Item 3.0</a>
					<ul>
						<li><a href="?3.0.0">Item 3.0.0</a></li>

						<li><a href="?3.0.1">Item 3.0.1</a>
							<ul>
								<li><a href="?3.0.1.0">Item 3.0.1.0</a></li>
								<li><a href="?3.0.1.1">Item 3.0.1.1</a></li>
							</ul>
						</li>
						<li><a href="?3.0.2">Item 3.0.2</a>

							<ul>
								<li><a href="?3.0.2.0">Item 3.0.2.0</a></li>
								<li><a href="?3.0.2.1">Item 3.0.2.1</a></li>
								<li><a href="?3.0.2.2">Item 3.0.2.2</a></li>
							</ul>
						</li>
					</ul>

				</li>
			</ul>
		</li>
	</ul>
